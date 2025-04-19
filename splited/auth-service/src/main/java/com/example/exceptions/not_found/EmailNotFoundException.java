package com.example.exceptions.not_found;

import com.example.exceptions.NotFoundException;
import com.example.exceptions.dto.ErrorDto;

public class EmailNotFoundException extends NotFoundException {

    private static String MESSAGE = "Email isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("email", MESSAGE);

    public EmailNotFoundException() {
        super(MESSAGE);
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
