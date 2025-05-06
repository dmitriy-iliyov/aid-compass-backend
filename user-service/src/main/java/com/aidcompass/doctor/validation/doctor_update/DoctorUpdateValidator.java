package com.aidcompass.doctor.validation.doctor_update;

import com.aidcompass.doctor.DoctorRepository;
import com.aidcompass.doctor.models.DoctorEntity;
import com.aidcompass.doctor.models.dto.DoctorUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class DoctorUpdateValidator implements ConstraintValidator<DoctorUpdate, DoctorUpdateDto> {

    private final DoctorRepository doctorRepository;


    @Override
    public boolean isValid(DoctorUpdateDto doctorUpdateDto, ConstraintValidatorContext constraintValidatorContext) {

        if (doctorRepository == null) {
            throw new IllegalStateException("DoctorRepository is not properly initialized!");
        }

        boolean hasErrors = false;
        constraintValidatorContext.disableDefaultConstraintViolation();

        if(doctorUpdateDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Doctor not passed!")
                    .addPropertyNode("doctor")
                    .addConstraintViolation();
            return false;
        }

        DoctorEntity licenseDoctor = doctorRepository.findByLicenseNumber(doctorUpdateDto.licenseNumber()).orElse(null);
        if (licenseDoctor != null && !Objects.equals(licenseDoctor.getId(), doctorUpdateDto.id())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Number is in use.")
                    .addPropertyNode("number")
                    .addConstraintViolation();
            hasErrors = true;
        }
        return !hasErrors;
    }
}
