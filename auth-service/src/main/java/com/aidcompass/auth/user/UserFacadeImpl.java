package com.aidcompass.auth.user;

import com.aidcompass.auth.clients.RecoveryRequestDto;
import com.aidcompass.auth.exceptions.not_found.EmailNotFoundException;
import com.aidcompass.auth.user.models.dto.*;
import com.aidcompass.common.global_exceptions.BaseNotFoundException;
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
    private final Validator validator;


    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        unconfirmedUserService.save(userRegistrationDto);
    }

    @Override
    @Transactional
    public void confirmByEmail(String email) {
        try {
            userService.confirmByEmail(email);
        } catch (BaseNotFoundException e) {
            SystemUserDto systemUserDto = unconfirmedUserService.systemFindByEmail(email);
            userService.save(systemUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
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
    public UserResponseDto update(UUID id, UserUpdateDto userUpdateDto) {
        SystemUserUpdateDto systemUserUpdateDto = userService.mapToUpdateDto(userUpdateDto);
        systemUserUpdateDto.setId(id);
        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUserUpdateDto);
        if(!bindingResult.isEmpty()) {
            throw new ConstraintViolationException(bindingResult);
        }
        return userService.update(systemUserUpdateDto);
    }

    @Override
    public void recoverPasswordByEmail(RecoveryRequestDto recoveryRequestDto) {
        if (!userService.existsByEmail(recoveryRequestDto.resource())) {
            throw new EmailNotFoundException();
        }
        userService.updatePasswordByEmail(recoveryRequestDto.resource(), recoveryRequestDto.password());
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
