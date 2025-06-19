package com.aidcompass.exceptions.doctor;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class FullDoctorNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_doctor", "Doctor not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
