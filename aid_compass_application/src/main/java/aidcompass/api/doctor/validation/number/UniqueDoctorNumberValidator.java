package aidcompass.api.doctor.validation.number;

import aidcompass.api.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueDoctorNumberValidator implements ConstraintValidator<UniqueDoctorNumber, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if(userRepository == null)
            return true;
        return !userRepository.existsByNumber(number);
    }
}
