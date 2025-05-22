package com.aidcompass.work_day.validation.annotations;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class WorkDayCreateValidator implements ConstraintValidator<WorkDay, WorkDayCreateDto> {

    @Override
    public boolean isValid(WorkDayCreateDto dto, ConstraintValidatorContext context) {

        boolean hasErrors = false;

        LocalDate date = dto.date();

        for (WorkIntervalCreateDto intervalDto: dto.workIntervals()) {
            if(!date.equals(intervalDto.date())) {
                context.buildConstraintViolationWithTemplate("All intervals should have the same date!").
                        addPropertyNode("work_intervals")
                        .addConstraintViolation();
                hasErrors = true;
            }
        }

        return !hasErrors;
    }
}
