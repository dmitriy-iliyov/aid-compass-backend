package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class JuristNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("jurist", "Jurist not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
