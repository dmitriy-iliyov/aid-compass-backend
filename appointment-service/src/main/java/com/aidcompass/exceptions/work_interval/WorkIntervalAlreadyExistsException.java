package com.aidcompass.exceptions.work_interval;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class WorkIntervalAlreadyExistsException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Interval all ready exist!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
