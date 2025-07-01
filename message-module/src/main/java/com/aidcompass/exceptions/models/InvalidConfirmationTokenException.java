package com.aidcompass.exceptions.models;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class InvalidConfirmationTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Confirmation code is invalid!";
    private final ErrorDto errorDto = new ErrorDto("code", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

    public InvalidConfirmationTokenException() {
        super();
    }
}
