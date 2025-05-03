package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class InvalidAttemptChangeToPrimaryException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("contact", "Unconfirmed contact can't be primary!");


    public InvalidAttemptChangeToPrimaryException() {
        super();
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
