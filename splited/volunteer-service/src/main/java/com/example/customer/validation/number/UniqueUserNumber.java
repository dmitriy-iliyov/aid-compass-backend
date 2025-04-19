package com.example.customer.validation.number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserNumberValidator.class)
@Documented
public @interface UniqueUserNumber {
    String message() default "Number should be unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
