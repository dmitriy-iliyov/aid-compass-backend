package com.aidcompass.auth.exceptions.not_found;

import com.aidcompass.common.global_exceptions.BaseNotFoundException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;

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
