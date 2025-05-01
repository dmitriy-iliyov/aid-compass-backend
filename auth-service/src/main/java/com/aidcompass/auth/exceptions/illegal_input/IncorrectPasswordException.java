package com.aidcompass.auth.exceptions.illegal_input;

import com.aidcompass.common.global_exceptions.BaseInvalidInputException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;


public class IncorrectPasswordException extends BaseInvalidInputException {

    private final static String MESSAGE = "Incorrect password!";
    private final ErrorDto errorDto = new ErrorDto("password", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
