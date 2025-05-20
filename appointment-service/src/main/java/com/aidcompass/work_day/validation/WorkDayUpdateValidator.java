package com.aidcompass.work_day.validation;

import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class WorkDayUpdateValidator implements ConstraintValidator<WorkDay, WorkDayUpdateDto> {

    @Override
    public boolean isValid(WorkDayUpdateDto dto, ConstraintValidatorContext context) {

        LocalDate date = dto.date();

        for (WorkIntervalUpdateDto intervalDto: dto.workIntervals()) {
            if (!intervalDto.date().equals(date)) {
                context.buildConstraintViolationWithTemplate("All intervals should have the same date!").
                        addPropertyNode("interval")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
