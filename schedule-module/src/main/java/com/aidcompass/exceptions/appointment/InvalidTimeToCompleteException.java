package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class InvalidTimeToCompleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Future or past appointment can't be complete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
