package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class FullJuristNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_jurist", "Jurist not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
