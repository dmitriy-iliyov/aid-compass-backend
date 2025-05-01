package com.aidcompass.auth.exceptions.not_found;

import com.aidcompass.common.global_exceptions.BaseNotFoundException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;

public class AuthorityNotFoundException extends BaseNotFoundException {

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
