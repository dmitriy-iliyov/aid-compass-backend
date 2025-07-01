package com.aidcompass.jurist.type;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class UnsupportedJuristTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("type", "Unsupported jurist type!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
