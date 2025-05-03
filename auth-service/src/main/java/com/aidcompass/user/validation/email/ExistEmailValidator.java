package com.aidcompass.user.validation.email;

import com.aidcompass.user.services.UserFacade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistEmailValidator implements ConstraintValidator<ExistEmail, String> {

    private final UserFacade userFacade;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        // надо проверять в общей базе контактов
        return userFacade.existsByEmail(email);
    }
}
