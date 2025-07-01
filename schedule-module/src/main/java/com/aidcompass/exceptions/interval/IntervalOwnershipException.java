package com.aidcompass.exceptions.interval;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class IntervalOwnershipException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Ownership exception!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
