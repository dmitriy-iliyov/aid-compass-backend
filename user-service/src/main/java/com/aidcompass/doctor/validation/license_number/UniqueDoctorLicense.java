package com.aidcompass.doctor.validation.license_number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDoctorLicenseNumberValidator.class)
@Documented
public @interface UniqueDoctorLicense {
    String message() default "License should be unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
