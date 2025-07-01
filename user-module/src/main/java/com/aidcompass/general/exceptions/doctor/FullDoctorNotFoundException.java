package com.aidcompass.general.exceptions.doctor;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class FullDoctorNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_doctor", "Doctor not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
