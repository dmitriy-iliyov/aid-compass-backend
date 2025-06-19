package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.models.BaseInvalidInputExceptionList;
import com.aidcompass.models.dto.ErrorDto;

import java.util.List;

public class BaseInvalidContactUpdateException extends BaseInvalidInputExceptionList {

    public List<ErrorDto> errorDtoList;


    public BaseInvalidContactUpdateException(List<ErrorDto> errors) {
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
