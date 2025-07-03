package com.aidcompass.system.exception;

import com.aidcompass.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class UnsupportedUserTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("user_type", "Unsupported user type!");
    }
}
