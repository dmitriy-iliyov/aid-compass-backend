package com.aidcompass.work_interval.validation.annotation;

import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WorkIntervalUpdateValidator implements ConstraintValidator<WorkInterval, WorkIntervalUpdateDto> {


    @Override
    public boolean isValid(WorkIntervalUpdateDto workIntervalUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
