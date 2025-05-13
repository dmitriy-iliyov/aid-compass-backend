package com.aidcompass.doctor.services;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class DoctorNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("doctor", "Doctor not found by id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
