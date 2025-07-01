package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class InvalidAttemptToDeleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Completed appointment can't be delete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
