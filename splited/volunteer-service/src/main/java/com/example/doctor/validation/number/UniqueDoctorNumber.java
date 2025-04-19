package com.example.doctor.validation.number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDoctorNumberValidator.class)
@Documented
public @interface UniqueDoctorNumber {
    String message() default "Number should be unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
