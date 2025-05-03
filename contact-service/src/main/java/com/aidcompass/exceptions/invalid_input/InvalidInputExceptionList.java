package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.Exception;
import com.aidcompass.global_exceptions.dto.ErrorDto;

import java.util.List;

public abstract class InvalidInputExceptionList extends Exception {

    abstract List<ErrorDto> getErrorDtoList();
}
