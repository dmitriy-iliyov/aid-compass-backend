package com.aidcompass.models;

import com.aidcompass.models.dto.ErrorDto;

public class PassedListIsToLongException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("ids", "List of id shouldn't have more then 10 elements!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
