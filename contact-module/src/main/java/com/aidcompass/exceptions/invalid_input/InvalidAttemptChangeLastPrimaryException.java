package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class InvalidAttemptChangeLastPrimaryException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("primary", "The last primary contact cant be change!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
