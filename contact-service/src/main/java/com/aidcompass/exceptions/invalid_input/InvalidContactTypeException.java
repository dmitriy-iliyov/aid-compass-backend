package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class InvalidContactTypeException extends BaseInvalidInputException {

    private final static String MESSAGE = "Invalid contact type!";
    private final ErrorDto errorDto = new ErrorDto("type", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
