package com.aidcompass.general.exceptions.doctor;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class DoctorNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("doctor", "Doctor not found by id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
