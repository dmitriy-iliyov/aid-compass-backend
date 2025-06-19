package com.aidcompass.exceptions.not_found;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class EmailNotFoundException extends BaseNotFoundException {

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
