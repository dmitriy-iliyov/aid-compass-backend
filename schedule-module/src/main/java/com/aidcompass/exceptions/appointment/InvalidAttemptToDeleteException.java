package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToDeleteException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Completed appointment can't be delete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
