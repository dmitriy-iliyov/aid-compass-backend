package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public class NotEnoughSpaseForNewContactException extends InvalidInputExceptionList {

    private List<ErrorDto> errorDtoList;


    public NotEnoughSpaseForNewContactException(List<ErrorDto> errorDtoList) {
        this.errorDtoList = errorDtoList;
    }

    @Override
    List<ErrorDto> getErrorDtoList() {
        return this.errorDtoList;
    }
}
