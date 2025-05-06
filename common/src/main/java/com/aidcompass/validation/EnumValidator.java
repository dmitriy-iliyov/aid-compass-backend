package com.aidcompass.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> validTypes;

    @Override
    public void initialize(ValidEnum annotation) {
        ConstraintValidator.super.initialize(annotation);
        validTypes = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(Enum::toString)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(validTypes);
        return type != null && validTypes.contains(type);
    }
}
