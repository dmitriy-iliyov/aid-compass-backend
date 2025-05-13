package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class InvalidDayTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("day_type", "Invalid day type!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
