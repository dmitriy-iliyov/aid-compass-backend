package aidcompass.api.doctor.validation.license_number;

import aidcompass.api.doctor.DoctorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueDoctorLicenseNumberValidator implements ConstraintValidator<UniqueDoctorLicenseNumber, String> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String licenseNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !doctorRepository.existsByLicenseNumber(licenseNumber);
    }
}
