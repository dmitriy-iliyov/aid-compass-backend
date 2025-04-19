package com.example.exceptions.models;

import com.example.exceptions.InvalidInputException;
import com.example.exceptions.dto.ErrorDto;

public class InvalidConfirmationTokenException extends InvalidInputException {

    private static final String MESSAGE = "Confirmation token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

    public InvalidConfirmationTokenException() {
        super();
    }

    public InvalidConfirmationTokenException(String message) {
        super(message);
    }
}
