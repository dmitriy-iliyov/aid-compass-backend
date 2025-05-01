package com.example.exceptions.models;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class BaseInvalidPasswordRecoveryTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Recovery token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
