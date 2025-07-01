package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class InvalidContactTypeException extends BaseInvalidInputException {

    private final static String MESSAGE = "Invalid contact type!";
    private final ErrorDto errorDto = new ErrorDto("type", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
