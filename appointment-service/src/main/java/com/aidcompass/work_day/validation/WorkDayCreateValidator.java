package com.aidcompass.work_day.validation;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class WorkDayCreateValidator implements ConstraintValidator<WorkDay, WorkDayCreateDto> {

    @Override
    public boolean isValid(WorkDayCreateDto dto, ConstraintValidatorContext context) {

        LocalDate date = dto.date();

        for (WorkIntervalCreateDto intervalDto: dto.workIntervals()) {
            if(!date.equals(intervalDto.date())) {
                context.buildConstraintViolationWithTemplate("All intervals should have the same date!").
                        addPropertyNode("interval")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
