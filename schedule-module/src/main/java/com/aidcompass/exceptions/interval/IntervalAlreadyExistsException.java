package com.aidcompass.exceptions.interval;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class IntervalAlreadyExistsException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Interval all ready exist!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
