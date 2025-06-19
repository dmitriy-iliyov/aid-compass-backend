package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.models.BaseInvalidInputExceptionList;
import com.aidcompass.models.dto.ErrorDto;

import java.util.List;

public class NotEnoughSpaseForNewContactExceptionBase extends BaseInvalidInputExceptionList {

    private List<ErrorDto> errorDtoList;


    public NotEnoughSpaseForNewContactExceptionBase(List<ErrorDto> errorDtoList) {
        this.errorDtoList = errorDtoList;
    }


    @Override
    public List<ErrorDto> getErrorDtoList() {
        return this.errorDtoList;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
