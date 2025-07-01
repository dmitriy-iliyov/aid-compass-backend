package com.aidcompass.exception;

import com.aidcompass.BaseInternalServerException;
import com.aidcompass.dto.ErrorDto;

public class UnsupportedUserTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("user_type", "Unsupported user type!");
    }
}
