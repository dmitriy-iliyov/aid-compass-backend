package com.aidcompass.exceptions.jurist;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class FullJuristNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_jurist", "Jurist not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
