package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class ProfileNotCompleteException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
