package com.example.exceptions.illegal_input;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;


public class IncorrectPasswordExceptionBase extends BaseInvalidInputException {

    private final static String MESSAGE = "Incorrect password!";
    private final ErrorDto errorDto = new ErrorDto("password", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
