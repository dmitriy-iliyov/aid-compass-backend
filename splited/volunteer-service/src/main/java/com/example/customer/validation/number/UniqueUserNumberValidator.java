package com.example.customer.validation.number;

import com.example.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserNumberValidator implements ConstraintValidator<UniqueUserNumber, String> {

    @Autowired
    private UserRepository userRepository;


    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
//        if(userRepository == null) {
//            throw new IllegalStateException("UserRepository is not properly initialized.");
//        }
//        return !userRepository.existsByNumber(number);
        return true;
    }
}
