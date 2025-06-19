package com.aidcompass.exceptions.doctor;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class DoctorNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("doctor", "Doctor not found by id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
