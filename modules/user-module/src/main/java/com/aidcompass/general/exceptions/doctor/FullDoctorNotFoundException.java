package com.aidcompass.general.exceptions.doctor;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class FullDoctorNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_doctor", "Doctor not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
