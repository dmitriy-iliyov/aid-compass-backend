package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class EmailAllReadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("email", "Email isn't unique!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
