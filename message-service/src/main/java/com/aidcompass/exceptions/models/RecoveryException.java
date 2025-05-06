package com.aidcompass.exceptions.models;

import com.aidcompass.global_exceptions.Exception;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class RecoveryException extends Exception {

    private final ErrorDto errorDto;

    public RecoveryException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
