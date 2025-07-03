package com.aidcompass.general.exceptions.gender;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class UnsupportedGenderException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("gender", "Unsupported gender!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
