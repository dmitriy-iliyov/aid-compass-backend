package aidcompass.api.doctor.validation.doctor_update;

import aidcompass.api.doctor.DoctorRepository;
import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class DoctorUpdateValidator implements ConstraintValidator<ValidDoctorUpdate, DoctorUpdateDto> {

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public boolean isValid(DoctorUpdateDto doctorUpdateDto, ConstraintValidatorContext constraintValidatorContext) {

        if (doctorRepository == null) {
            throw new IllegalStateException("DoctorRepository is not properly initialized.");
        }

        boolean hasErrors = false;
        constraintValidatorContext.disableDefaultConstraintViolation();

        if(doctorUpdateDto == null){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Doctor not passed!")
                    .addPropertyNode("doctor")
                    .addConstraintViolation();
            return false;
        }

        DoctorEntity emailDoctor = doctorRepository.findByEmail(doctorUpdateDto.getEmail()).orElse(null);
        if (emailDoctor != null && !Objects.equals(emailDoctor.getId(), doctorUpdateDto.getId())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email is in use!")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            hasErrors = true;
        }
        DoctorEntity phoneDoctor = doctorRepository.findByNumber(doctorUpdateDto.getNumber()).orElse(null);
        if (phoneDoctor != null && !Objects.equals(phoneDoctor.getId(), doctorUpdateDto.getId())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Number is in use!")
                    .addPropertyNode("number")
                    .addConstraintViolation();
            hasErrors = true;
        }
        DoctorEntity licenseDoctor = doctorRepository.findByLicenseNumber(doctorUpdateDto.getLicenseNumber()).orElse(null);
        if (licenseDoctor != null && !Objects.equals(licenseDoctor.getId(), doctorUpdateDto.getId())){
            constraintValidatorContext.buildConstraintViolationWithTemplate("Number is in use!")
                    .addPropertyNode("number")
                    .addConstraintViolation();
            hasErrors = true;
        }
        return !hasErrors;
    }
}
