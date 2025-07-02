package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class FullJuristNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_jurist", "Jurist not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
