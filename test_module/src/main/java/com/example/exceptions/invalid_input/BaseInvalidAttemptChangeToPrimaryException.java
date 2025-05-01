package com.example.exceptions.invalid_input;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class BaseInvalidAttemptChangeToPrimaryException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("contact", "Unconfirmed contact can't be primary!");


    public BaseInvalidAttemptChangeToPrimaryException() {
        super();
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
