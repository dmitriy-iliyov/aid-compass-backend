package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public class InvalidContactUpdateException extends InvalidInputExceptionList {

    public List<ErrorDto> errorDtoList;


    public InvalidContactUpdateException(List<ErrorDto> errors) {
        this.errorDtoList = errors;
    }

    @Override
    public List<ErrorDto> getErrorDtoList() {
        return this.errorDtoList;
    }
}
