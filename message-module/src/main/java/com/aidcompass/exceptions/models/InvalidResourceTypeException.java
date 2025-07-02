package com.aidcompass.exceptions.models;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidResourceTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("resource_type", "Invalid resource type!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
