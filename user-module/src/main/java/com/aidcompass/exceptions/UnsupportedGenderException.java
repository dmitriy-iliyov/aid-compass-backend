package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class UnsupportedGenderException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("gender", "Unsupported gender!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
