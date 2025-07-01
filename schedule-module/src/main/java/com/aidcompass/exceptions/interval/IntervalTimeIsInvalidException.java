package com.aidcompass.exceptions.interval;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class IntervalTimeIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Non-working hours!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
