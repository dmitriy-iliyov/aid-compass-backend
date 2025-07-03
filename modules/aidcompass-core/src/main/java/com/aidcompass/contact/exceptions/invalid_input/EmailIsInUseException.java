package com.aidcompass.contact.exceptions.invalid_input;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class EmailIsInUseException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("email", "Email already in use!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
