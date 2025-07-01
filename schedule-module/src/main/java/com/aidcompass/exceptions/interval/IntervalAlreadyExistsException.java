package com.aidcompass.exceptions.interval;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class IntervalAlreadyExistsException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Interval all ready exist!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
