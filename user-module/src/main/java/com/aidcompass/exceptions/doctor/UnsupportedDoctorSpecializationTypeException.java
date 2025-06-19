package com.aidcompass.exceptions.doctor;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class UnsupportedDoctorSpecializationTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported doctor specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
