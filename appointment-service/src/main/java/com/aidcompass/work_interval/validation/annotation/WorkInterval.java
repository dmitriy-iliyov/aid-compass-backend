package com.aidcompass.work_interval.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {WorkIntervalCreateValidator.class, WorkIntervalUpdateValidator.class})
@Documented
public @interface WorkInterval {
    String message() default "Work interval is invalid!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
