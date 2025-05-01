package com.example.exceptions.not_found;

import com.example.global_exceptions.BaseNotFoundException;
import com.example.global_exceptions.dto.ErrorDto;

public class EmailBaseNotFoundException extends BaseNotFoundException {

    private static String MESSAGE = "Email isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("email", MESSAGE);

    public EmailBaseNotFoundException() {
        super(MESSAGE);
    }

    public EmailBaseNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
