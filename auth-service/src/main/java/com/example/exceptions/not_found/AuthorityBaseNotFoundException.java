package com.example.exceptions.not_found;

import com.example.global_exceptions.BaseNotFoundException;
import com.example.global_exceptions.dto.ErrorDto;

public class AuthorityBaseNotFoundException extends BaseNotFoundException {

    private static String MESSAGE = "Authority isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("authority", MESSAGE);

    public AuthorityBaseNotFoundException() {
        super(MESSAGE);
    }

    public AuthorityBaseNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

}
