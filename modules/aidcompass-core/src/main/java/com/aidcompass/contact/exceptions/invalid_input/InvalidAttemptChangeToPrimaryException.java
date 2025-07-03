package com.aidcompass.contact.exceptions.invalid_input;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

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
