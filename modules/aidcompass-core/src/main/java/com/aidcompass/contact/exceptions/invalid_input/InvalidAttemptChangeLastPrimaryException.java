package com.aidcompass.contact.exceptions.invalid_input;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidAttemptChangeLastPrimaryException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("primary", "The last primary contact cant be change!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
