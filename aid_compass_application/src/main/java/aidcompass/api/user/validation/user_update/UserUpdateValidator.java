package aidcompass.api.user.validation.user_update;

import aidcompass.api.user.UserRepository;
import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class UserUpdateValidator implements ConstraintValidator<ValidUserUpdate, UserUpdateDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserUpdateDto userUpdateDto, ConstraintValidatorContext constraintValidatorContext)
            throws IllegalArgumentException, EntityNotFoundException {

        if (userRepository == null) {
            throw new IllegalStateException("UserRepository is not properly initialized.");
        }

        boolean hasErrors = false;

        constraintValidatorContext.disableDefaultConstraintViolation();

        if(userUpdateDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("User not passed!")
                    .addPropertyNode("user")
                    .addConstraintViolation();
            return false;
        }

        UserEntity emailUser = userRepository.findByEmail(userUpdateDto.getEmail()).orElse(null);
        if (emailUser != null && !Objects.equals(emailUser.getId(), userUpdateDto.getId())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email is in use!")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            hasErrors = true;
        }

        if (userUpdateDto.getNumber() != null) {
            UserEntity phoneUser = userRepository.findByNumber(userUpdateDto.getNumber()).orElse(null);
            if (phoneUser != null && !Objects.equals(phoneUser.getId(), userUpdateDto.getId())){
                constraintValidatorContext.buildConstraintViolationWithTemplate("Number is in use!")
                        .addPropertyNode("number")
                        .addConstraintViolation();
                hasErrors = true;
            }
        } else
            return false;
        return !hasErrors;
    }
}
