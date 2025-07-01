package com.aidcompass.user.services;

import com.aidcompass.dto.security.RecoveryRequestDto;
import com.aidcompass.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.dto.system.SystemContactCreateDto;
import com.aidcompass.dto.user.SystemUserDto;
import com.aidcompass.dto.user.UserRegistrationDto;
import com.aidcompass.dto.user.UserResponseDto;
import com.aidcompass.dto.user.UserUpdateDto;
import com.aidcompass.contracts.ContactServiceSyncOrchestrator;
import com.aidcompass.contracts.UserOrchestrator;
import com.aidcompass.enums.ContactType;
import com.aidcompass.exceptions.not_found.EmailNotFoundException;
import com.aidcompass.BaseNotFoundException;
import com.aidcompass.user.models.SystemUserUpdateDto;
import com.aidcompass.uuid.UuidFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrchestratorImpl implements UserOrchestrator {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final ContactServiceSyncOrchestrator synchronizationFacade;
    private final Validator validator;


    @Override
    public void save(UserRegistrationDto dto) {
        UUID id = UuidFactory.generate();
        if (userService.existsById(id)) {
            id = UuidFactory.generate();
            log.error("Id duplicated id: {}", id);
        }
        synchronizationFacade.save(new SystemContactCreateDto(id, ContactType.EMAIL, dto.email()));
        unconfirmedUserService.save(id, dto);
    }

    @Transactional
    @Override
    public void confirmByEmail(String email) {
        Long emailId = synchronizationFacade.confirmContact(new SystemConfirmationRequestDto(email));
        try {
            userService.confirmByEmail(email, emailId);
        } catch (BaseNotFoundException e) {
            SystemUserDto systemUserDto = unconfirmedUserService.systemFindByEmail(email, emailId);
            userService.save(systemUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email) || unconfirmedUserService.existsByEmail(email);
    }

    @Override
    public boolean existsById(UUID id) {
        return userService.existsById(id);
    }

    @Override
    public SystemUserDto systemFindById(UUID id) {
        return userService.systemFindById(id);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) throws BaseNotFoundException {
        try {
            return userService.systemFindByEmail(email);
        } catch (BaseNotFoundException e) {
            return unconfirmedUserService.systemFindByEmail(email);
        }
    }

    @Override
    public UserResponseDto findById(UUID id) {
        return userService.findById(id);
    }

    @Override
    public UserResponseDto update(UUID id, UserUpdateDto updateDto) {
        SystemUserUpdateDto systemUpdateDto = userService.mapToUpdateDto(updateDto);
        systemUpdateDto.setId(id);
        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUpdateDto);
        if(!bindingResult.isEmpty()) {
            throw new ConstraintViolationException(bindingResult);
        }
        return userService.update(systemUpdateDto);
    }

    @Override
    public void recoverPasswordByEmail(RecoveryRequestDto recoveryRequest) {
        if (!userService.existsByEmail(recoveryRequest.resource())) {
            throw new EmailNotFoundException();
        }
        userService.updatePasswordByEmail(recoveryRequest.resource(), recoveryRequest.password());
    }

    @Override
    public void deleteById(UUID id) {
        userService.deleteById(id);
    }

    @Override
    public void deleteByPassword(UUID id, String password) {
        userService.deleteByPassword(id, password);
    }
}
