package com.aidcompass.user.validation.user_update;


import com.aidcompass.authority.models.Authority;
import com.aidcompass.clients.confirmation.ConfirmationService;
import com.aidcompass.user.services.UserFacade;
import com.aidcompass.user.models.dto.SystemUserDto;
import com.aidcompass.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.global_exceptions.BaseNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, SystemUserUpdateDto> {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;


    @Override
    public boolean isValid(SystemUserUpdateDto systemUserUpdateDto, ConstraintValidatorContext constraintValidatorContext)
            throws IllegalArgumentException, EntityNotFoundException {

        if (userFacade == null) {
            throw new IllegalStateException("UserFacade is not properly initialized.");
        }

        boolean hasErrors = false;

        if(systemUserUpdateDto == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("User not passed!")
                    .addPropertyNode("user")
                    .addConstraintViolation();
            return false;
        }

        SystemUserDto existedUser;
        try {
            // надо проверять в общей базе контактов
            existedUser = userFacade.systemFindByEmail(systemUserUpdateDto.getEmail());
            if(!existedUser.id().equals(systemUserUpdateDto.getId())) {
                hasErrors = true;
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email already in use!")
                        .addPropertyNode("email")
                        .addConstraintViolation();
            } else {
                systemUserUpdateDto.setAuthorities(existedUser.authorities());
            }
        } catch (BaseNotFoundException e) {
            List<Authority> authorities = userFacade.systemFindById(systemUserUpdateDto.getId()).authorities();
            authorities.remove(Authority.ROLE_USER);
            authorities.add(Authority.ROLE_UNCONFIRMED_USER);
            confirmationService.sendConfirmationMessage(systemUserUpdateDto.getEmail());
            systemUserUpdateDto.setAuthorities(authorities);
            return true;
        }

//        if (systemUserUpdateDto.getNumber() != null) {
//            SystemUserDto phoneUser = userFacade.systemFindByNumber(systemUserUpdateDto.getNumber());
//            if (phoneUser != null && !Objects.equals(phoneUser.id(), systemUserUpdateDto.getId())) {
//                hasErrors = true;
//                constraintValidatorContext.buildConstraintViolationWithTemplate("Number is in use!")
//                        .addPropertyNode("number")
//                        .addConstraintViolation();
//            }
//        } else
//            return false;
        return !hasErrors;
    }
}
