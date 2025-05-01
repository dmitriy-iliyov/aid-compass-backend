package com.example.exceptions.models;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class BaseInvalidConfirmationTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Confirmation token is invalid!";
    private final ErrorDto errorDto = new ErrorDto("token", MESSAGE);


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

    public BaseInvalidConfirmationTokenException() {
        super();
    }
}
