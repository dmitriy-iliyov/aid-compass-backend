package com.example.exceptions.not_found;

import com.example.exceptions.NotFoundException;
import com.example.exceptions.dto.ErrorDto;

public class AuthorityNotFoundException extends NotFoundException {

    private static String MESSAGE = "Authority isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("authority", MESSAGE);

    public AuthorityNotFoundException() {
        super(MESSAGE);
    }

    public AuthorityNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

}
