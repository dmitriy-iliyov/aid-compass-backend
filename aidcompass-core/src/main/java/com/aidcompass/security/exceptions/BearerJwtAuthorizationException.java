package com.aidcompass.security.exceptions;

import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class BearerJwtAuthorizationException extends BaseAuthorizationException {

    private String message;

    public BearerJwtAuthorizationException(String message) {
        this.message = message;
    }

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("bearer_token", message);
    }
}
