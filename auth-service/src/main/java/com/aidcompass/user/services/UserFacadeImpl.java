package com.aidcompass.user.services;

import com.aidcompass.clients.RecoveryRequestDto;
import com.aidcompass.clients.contacts.ContactService;
import com.aidcompass.exceptions.illegal_input.EmailAllReadyExistException;
import com.aidcompass.exceptions.not_found.EmailNotFoundException;
import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.user.models.dto.*;
import com.aidcompass.utils.uuid.UuidFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final ContactService contactService;
    private final Validator validator;


    @Override
    public void save(UserRegistrationDto dto) {
        UUID id = UuidFactory.generate();
        String email = contactService.createContact(id, dto.email());
        if(email == null) {
            throw new EmailAllReadyExistException();
        }
        unconfirmedUserService.save(id, dto);
    }

    @Transactional
    @Override
    public void confirmByEmail(String email) {
        try {
            userService.confirmByEmail(email);
        } catch (BaseNotFoundException e) {
            SystemUserDto systemUserDto = unconfirmedUserService.systemFindByEmail(email);
            userService.save(systemUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
        contactService.confirmContact(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email) || unconfirmedUserService.existsByEmail(email);
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

//    @Override
//    public PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest) {
//        return userService.findAll(filter, pageRequest);
//    }

    @Override
    public UserResponseDto update(UUID id, UserUpdateDto updateDto) {
        SystemUserUpdateDto systemUserUpdateDto = userService.mapToUpdateDto(updateDto);
        systemUserUpdateDto.setId(id);
        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUserUpdateDto);
        if(!bindingResult.isEmpty()) {
            throw new ConstraintViolationException(bindingResult);
        }
        return userService.update(systemUserUpdateDto);
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
