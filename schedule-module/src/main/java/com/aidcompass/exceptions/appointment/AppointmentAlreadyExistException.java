package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class AppointmentAlreadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "You already have appointment at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
