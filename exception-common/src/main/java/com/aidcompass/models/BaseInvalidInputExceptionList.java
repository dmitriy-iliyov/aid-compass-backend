package com.aidcompass.models;

import com.aidcompass.models.dto.ErrorDto;

import java.util.List;

public abstract class BaseInvalidInputExceptionList extends Exception {

    public abstract List<ErrorDto> getErrorDtoList();
}
