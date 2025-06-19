package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class InvalidPrincipalPassed extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("cookie", "Invalid auth token passed!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
