package com.example.user.validation.user_update;


import com.example.authority.models.Authority;
import com.example.global_exceptions.BaseNotFoundException;
import com.example.clients.confirmation.ConfirmationService;
import com.example.user.UserFacade;
import com.example.user.models.dto.SystemUserDto;
import com.example.user.models.dto.SystemUserUpdateDto;
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
