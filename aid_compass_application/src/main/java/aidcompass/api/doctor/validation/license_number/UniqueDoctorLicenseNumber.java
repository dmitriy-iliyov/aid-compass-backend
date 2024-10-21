package aidcompass.api.doctor.validation.license_number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDoctorLicenseNumberValidator.class)
@Documented
public @interface UniqueDoctorLicenseNumber {
    String message() default "License number isn't unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
