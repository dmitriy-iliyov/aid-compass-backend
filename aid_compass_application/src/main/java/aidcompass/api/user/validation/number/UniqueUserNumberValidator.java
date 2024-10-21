package aidcompass.api.user.validation.number;

import aidcompass.api.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserNumberValidator implements ConstraintValidator<UniqueUserNumber, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if(userRepository == null)
            return true;
        return !userRepository.existsByNumber(number);
    }
}
