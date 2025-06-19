package com.aidcompass.exceptions.jurist;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class UnsupportedJuristSpecializationTypeException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported jurist specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
