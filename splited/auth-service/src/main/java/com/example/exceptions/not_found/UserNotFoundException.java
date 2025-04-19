package com.example.exceptions.not_found;

import com.example.exceptions.NotFoundException;
import com.example.exceptions.dto.ErrorDto;

public class UserNotFoundException extends NotFoundException {

    private static String MESSAGE = "User isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("user", MESSAGE);

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
