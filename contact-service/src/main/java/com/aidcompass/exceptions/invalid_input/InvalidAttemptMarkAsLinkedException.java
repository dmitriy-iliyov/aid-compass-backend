package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public class InvalidAttemptMarkAsLinkedException extends InvalidInputExceptionList {

    private List<ErrorDto> errorDtoList;

    public InvalidAttemptMarkAsLinkedException(List<ErrorDto> errors) {
        this.errorDtoList = errors;
    }

    @Override
    List<ErrorDto> getErrorDtoList() {
        return this.errorDtoList;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
