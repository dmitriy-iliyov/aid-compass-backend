package com.aidcompass.general.exceptions;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class ProfileNotCompleteException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
