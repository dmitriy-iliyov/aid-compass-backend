package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class TokenExpired extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("jwt", "Jwt has expired!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
