package com.example.exceptions.models;

import com.example.exceptions.InvalidInputException;
import com.example.exceptions.dto.ErrorDto;

public class InvalidPasswordRecoveryTokenException extends InvalidInputException {

    private static final String MESSAGE = "Recovery token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);


    public InvalidPasswordRecoveryTokenException() {
        super(MESSAGE);
    }

    public InvalidPasswordRecoveryTokenException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
