package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class DayTypeEntityNotFoundByDayTypeException extends BaseNotFoundException {

    private final ErrorDto errorDto =new ErrorDto("day_type", "Type not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
