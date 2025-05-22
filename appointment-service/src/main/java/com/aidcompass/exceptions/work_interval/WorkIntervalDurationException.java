package com.aidcompass.exceptions.work_interval;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class WorkIntervalDurationException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Invalid duration!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
