package com.aidcompass.work_day.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {WorkDayCreateValidator.class, WorkDayUpdateValidator.class})
@Documented
public @interface WorkDay {
    String message() default "Work day has invalid data!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
