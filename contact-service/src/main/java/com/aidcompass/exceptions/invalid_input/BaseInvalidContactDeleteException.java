package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.BaseInvalidInputExceptionList;
import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public class BaseInvalidContactDeleteException extends BaseInvalidInputExceptionList {

    private List<ErrorDto> errorDtoList;


    public BaseInvalidContactDeleteException(List<ErrorDto> errors) {
        this.errorDtoList = errors;
    }

    @Override
    public List<ErrorDto> getErrorDtoList() {
        return errorDtoList;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
