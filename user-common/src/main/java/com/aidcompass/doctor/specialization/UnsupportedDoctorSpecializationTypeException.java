package com.aidcompass.doctor.specialization;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class UnsupportedDoctorSpecializationTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported doctor specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
