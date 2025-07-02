package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class AppointmentOwnershipException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "This is not your appointment!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
