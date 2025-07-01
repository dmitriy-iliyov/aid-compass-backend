package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;


public class IncorrectPasswordException extends BaseInvalidInputException {

    private final static String MESSAGE = "Incorrect password!";
    private final ErrorDto errorDto = new ErrorDto("password", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
