package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class UnsupportedJuristTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("type", "Unsupported jurist type!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
