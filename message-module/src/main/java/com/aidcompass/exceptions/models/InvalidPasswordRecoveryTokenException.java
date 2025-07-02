package com.aidcompass.exceptions.models;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidPasswordRecoveryTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Recovery code is invalid!";
    private final ErrorDto errorDto = new ErrorDto("code", MESSAGE);

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
