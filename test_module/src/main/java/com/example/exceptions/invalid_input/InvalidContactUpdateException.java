package com.example.exceptions.invalid_input;

import com.example.global_exceptions.dto.ErrorDto;

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
