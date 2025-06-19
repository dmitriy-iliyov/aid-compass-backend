package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class ProfileNotCompleteException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
