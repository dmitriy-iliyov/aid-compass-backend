package com.aidcompass.work_day.validation.annotations;

import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class WorkDayUpdateValidator implements ConstraintValidator<WorkDay, WorkDayUpdateDto> {

    @Override
    public boolean isValid(WorkDayUpdateDto dto, ConstraintValidatorContext context) {

        boolean hasErrors = false;

        LocalDate date = dto.date();

        for (WorkIntervalUpdateDto intervalDto: dto.workIntervals()) {
            if (!intervalDto.date().equals(date)) {
                context.buildConstraintViolationWithTemplate("All intervals should have the same date!").
                        addPropertyNode("work_intervals")
                        .addConstraintViolation();
                hasErrors = true;
            }
        }
        return !hasErrors;
    }
}
