package com.aidcompass.exceptions.interval;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class IntervalIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Start of work interval is after end!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
