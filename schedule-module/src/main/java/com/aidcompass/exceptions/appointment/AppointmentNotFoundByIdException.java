package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class AppointmentNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Appointment not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
