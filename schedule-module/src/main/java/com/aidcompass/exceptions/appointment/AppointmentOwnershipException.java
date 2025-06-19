package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class AppointmentOwnershipException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "This is not your appointment!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
