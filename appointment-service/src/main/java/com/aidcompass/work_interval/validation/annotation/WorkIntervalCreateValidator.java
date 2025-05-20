package com.aidcompass.work_interval.validation.annotation;

import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WorkIntervalCreateValidator implements ConstraintValidator<WorkInterval, WorkIntervalCreateDto> {

    // должен быть больше или равен длинне приема
    // рамки приема
    @Override
    public boolean isValid(WorkIntervalCreateDto workIntervalCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
