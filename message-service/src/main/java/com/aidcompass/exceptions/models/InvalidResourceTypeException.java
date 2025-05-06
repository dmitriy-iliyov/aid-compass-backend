package com.aidcompass.exceptions.models;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class InvalidResourceTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("resource_type", "Invalid resource type!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
