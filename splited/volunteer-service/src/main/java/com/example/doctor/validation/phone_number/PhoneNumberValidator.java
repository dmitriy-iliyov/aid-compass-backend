package com.example.doctor.validation.phone_number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private final Pattern numberPattern = Pattern.compile("^[+]\\d{3}-?\\d{2}-?\\d{3}-?\\d{2}-?\\d{2}$");

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        return numberPattern.matcher(number).matches();
    }
}
