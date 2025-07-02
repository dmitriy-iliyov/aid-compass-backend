package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToCompleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Deleted appointment can't be complete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
