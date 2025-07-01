package com.aidcompass.exceptions.models;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class RecoveryException extends BaseInvalidInputException {

    private final ErrorDto errorDto;

    public RecoveryException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
