package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class AppointmentAlreadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "You already have appointment at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
