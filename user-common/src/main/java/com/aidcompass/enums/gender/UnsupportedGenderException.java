package com.aidcompass.enums.gender;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class UnsupportedGenderException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("gender", "Unsupported gender!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
