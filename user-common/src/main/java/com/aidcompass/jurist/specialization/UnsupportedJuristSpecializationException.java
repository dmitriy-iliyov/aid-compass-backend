package com.aidcompass.jurist.specialization;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class UnsupportedJuristSpecializationException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported jurist specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
