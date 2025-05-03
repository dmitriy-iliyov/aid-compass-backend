package com.aidcompass.user.validation.email;

import com.aidcompass.user.services.UserFacade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserFacade userFacade;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // надо проверять в общей базе контактов
        return !userFacade.existsByEmail(email);
    }
}

