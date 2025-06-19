package com.aidcompass.exceptions.jurist;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class JuristTypeNotFoundByTypeException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("type", "Jurist type not  found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
