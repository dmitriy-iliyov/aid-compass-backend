package com.aidcompass.auth.user.validation.email;

import com.aidcompass.auth.user.UserFacade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserFacade userFacade;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userFacade.existsByEmail(email);
    }
}

