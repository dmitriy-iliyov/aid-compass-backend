package com.aidcompass.exceptions.interval;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class IntervalTimeIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Non-working hours!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
