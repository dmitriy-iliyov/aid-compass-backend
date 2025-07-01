package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class EmailAllReadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("email", "Email isn't unique!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
