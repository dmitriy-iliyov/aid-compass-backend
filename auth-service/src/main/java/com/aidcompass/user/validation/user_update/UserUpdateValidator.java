package com.aidcompass.user.validation.user_update;


import com.aidcompass.authority.models.Authority;
import com.aidcompass.clients.confirmation.ConfirmationService;
import com.aidcompass.clients.contacts.ContactService;
import com.aidcompass.user.services.UserFacade;
import com.aidcompass.user.models.dto.SystemUserDto;
import com.aidcompass.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.global_exceptions.BaseNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

// move somewhere can't be in annotation validator
@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, SystemUserUpdateDto> {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;
    private final ContactService contactService;


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

        // может в один запрос обьеденить
        try {
            // надо проверять в общей базе контактов
            boolean isEmailExist = contactService.isEmailExist(systemUserUpdateDto.getEmail());
            if(isEmailExist) {
                hasErrors = true;
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email already in use!")
                        .addPropertyNode("email")
                        .addConstraintViolation();
            } else {
                SystemUserDto existedUser = userFacade.systemFindByEmail(systemUserUpdateDto.getEmail());
                systemUserUpdateDto.setAuthorities(existedUser.authorities());
            }
        } catch (BaseNotFoundException e) {

            List<Authority> authorities = userFacade.systemFindById(systemUserUpdateDto.getId()).authorities();
            authorities.remove(Authority.ROLE_USER);
            authorities.add(Authority.ROLE_UNCONFIRMED_USER);

            contactService.updateContact(systemUserUpdateDto.getEmail());
            confirmationService.sendConfirmationMessage(systemUserUpdateDto.getEmail());

            systemUserUpdateDto.setAuthorities(authorities);
            return true;
        }
        return !hasErrors;
    }
}
