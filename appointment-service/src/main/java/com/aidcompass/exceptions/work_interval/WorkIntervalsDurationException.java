package com.aidcompass.exceptions.work_interval;

import com.aidcompass.global_exceptions.BaseInvalidInputExceptionList;
import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public class WorkIntervalsDurationException extends BaseInvalidInputExceptionList {

    private final List<ErrorDto> errors;


    public WorkIntervalsDurationException(List<ErrorDto> errors) {
        this.errors = errors;
    }

    @Override
    public List<ErrorDto> getErrorDtoList() {
        return this.errors;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
