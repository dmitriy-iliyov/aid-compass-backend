package com.aidcompass.exceptions;

import com.aidcompass.models.dto.ErrorDto;

public class CookieAuthorizationException extends BaseAuthorizationException {

    private String message;

    public CookieAuthorizationException(String message) {
        this.message = message;
    }

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("cookie", message);
    }
}
