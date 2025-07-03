package com.aidcompass.exceptions.models;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

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
