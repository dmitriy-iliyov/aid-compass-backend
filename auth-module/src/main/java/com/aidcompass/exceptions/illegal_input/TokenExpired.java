package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class JwtExpired extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("jwt", "Jwt has expired!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
