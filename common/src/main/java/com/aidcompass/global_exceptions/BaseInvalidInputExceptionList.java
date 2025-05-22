package com.aidcompass.global_exceptions;

import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public abstract class BaseInvalidInputExceptionList extends Exception {

    public abstract List<ErrorDto> getErrorDtoList();
}
