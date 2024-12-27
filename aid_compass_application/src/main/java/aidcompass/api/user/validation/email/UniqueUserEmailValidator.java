package aidcompass.api.user.validation.email;

import aidcompass.api.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository == null) {
            throw new IllegalStateException("UserRepository is not properly initialized.");
        }
        return !userRepository.existsByEmail(email);
    }
}

