package com.aidcompass.exceptions.interval;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class IntervalNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
