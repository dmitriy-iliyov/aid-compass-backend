package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class InvalidTimeToCancelException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Past appointment can't be cancel!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
