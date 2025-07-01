package com.aidcompass.exceptions.interval;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class NearestIntervalNotFoundByOwnerIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("nearest_work_interval", "Nearest interval not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
