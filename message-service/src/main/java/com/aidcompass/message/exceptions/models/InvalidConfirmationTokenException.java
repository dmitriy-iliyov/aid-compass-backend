package com.aidcompass.message.exceptions.models;

import com.aidcompass.common.global_exceptions.BaseInvalidInputException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;

public class InvalidConfirmationTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Confirmation token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

    public InvalidConfirmationTokenException() {
        super();
    }
}
