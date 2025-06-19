package com.aidcompass.exceptions.illegal_input;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class CookieJwtExpired extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("cookie", "Cookie has expired!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
