package com.example.exceptions.invalid_input;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class BaseInvalidContactTypeException extends BaseInvalidInputException {

    private final static String MESSAGE = "Invalid contact type!";
    private final ErrorDto errorDto = new ErrorDto("type", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
