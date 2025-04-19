package com.example.exceptions.illegal_input;

import com.example.exceptions.InvalidInputException;
import com.example.exceptions.dto.ErrorDto;


public class IncorrectPasswordException extends InvalidInputException {

    private final static String MESSAGE = "Incorrect password!";
    private final ErrorDto errorDto = new ErrorDto("password", MESSAGE);


    public IncorrectPasswordException() {
        super(MESSAGE);
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
