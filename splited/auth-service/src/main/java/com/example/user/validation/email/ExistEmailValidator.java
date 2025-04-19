package com.example.user.validation.email;

import com.example.user.UserFacade;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistEmailValidator implements ConstraintValidator<ExistEmail, String> {

    private final UserFacade userFacade;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userFacade.existsByEmail(email);
    }
}
