package com.aidcompass;

import com.aidcompass.dto.ErrorDto;

import java.util.List;

public abstract class BaseInvalidInputExceptionList extends Exception {

    public abstract List<ErrorDto> getErrorDtoList();
}
