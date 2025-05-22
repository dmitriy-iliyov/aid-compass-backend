package com.aidcompass.work_interval.validation.annotation;

import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.validation.time.TimeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class WorkIntervalUpdateValidator implements ConstraintValidator<WorkInterval, WorkIntervalUpdateDto> {

    private final TimeValidator timeValidator;


    @Override
    public boolean isValid(WorkIntervalUpdateDto dto, ConstraintValidatorContext context) {
        boolean hasErrors = false;

        if (!timeValidator.isWorkIntervalValid(dto)) {
            context.buildConstraintViolationWithTemplate("Start of work interval is after end!")
                    .addPropertyNode("work_interval")
                    .addConstraintViolation();
            hasErrors = true;
        }

        if (!timeValidator.isWorkIntervalTimeValid(dto)) {
            hasErrors = true;
        }

        return !hasErrors;    }
}
