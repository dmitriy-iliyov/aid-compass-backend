package com.example.exceptions.invalid_input;

import com.example.global_exceptions.dto.ErrorDto;

import java.util.List;

public class InvalidContactDeleteException extends InvalidInputExceptionList {

    private List<ErrorDto> errorDtoList;


    public InvalidContactDeleteException(List<ErrorDto> errors) {
        this.errorDtoList = errors;
    }

    @Override
    List<ErrorDto> getErrorDtoList() {
        return List.of();
    }
}
