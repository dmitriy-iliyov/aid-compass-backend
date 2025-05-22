package com.aidcompass.exceptions.work_interval;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class WorkIntervalNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
