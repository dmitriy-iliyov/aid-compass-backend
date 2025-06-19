package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class NotWorkingAtThisTimeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Can't add appointment because specialist not working at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
