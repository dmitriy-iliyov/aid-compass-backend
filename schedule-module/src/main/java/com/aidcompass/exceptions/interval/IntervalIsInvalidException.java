package com.aidcompass.exceptions.interval;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class IntervalIsInvalidException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Start of work interval is after end!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
