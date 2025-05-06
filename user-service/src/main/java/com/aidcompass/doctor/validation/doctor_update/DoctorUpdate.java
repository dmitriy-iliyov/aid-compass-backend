package com.aidcompass.doctor.validation.doctor_update;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorUpdateValidator.class)
@Documented
public @interface DoctorUpdate {
    String message() default "Invalid doctor update.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
