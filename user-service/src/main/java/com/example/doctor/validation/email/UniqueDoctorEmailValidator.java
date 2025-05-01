package com.example.doctor.validation.email;

import aidcompass.api.doctor.DoctorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueDoctorEmailValidator implements ConstraintValidator<UniqueDoctorEmail, String> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (doctorRepository == null) {
            throw new IllegalStateException("DoctorRepository is not properly initialized.");
        }
        return !doctorRepository.existsByEmail(email);
    }
}

