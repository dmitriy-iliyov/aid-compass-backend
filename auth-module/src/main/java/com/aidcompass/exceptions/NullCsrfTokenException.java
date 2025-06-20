package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class NullCsrfTokenException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Passwd csrf token is null!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
