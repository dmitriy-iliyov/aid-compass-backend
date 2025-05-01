package com.aidcompass.message.exceptions.models;

import com.aidcompass.common.global_exceptions.BaseInvalidInputException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;

public class InvalidPasswordRecoveryTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Recovery token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
