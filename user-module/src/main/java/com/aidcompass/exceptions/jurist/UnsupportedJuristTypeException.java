package com.aidcompass.exceptions.jurist;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class UnsupportedJuristTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("type", "Unsupported jurist type!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
