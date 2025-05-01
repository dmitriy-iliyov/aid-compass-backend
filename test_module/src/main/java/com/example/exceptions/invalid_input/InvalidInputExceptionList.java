package com.example.exceptions.invalid_input;

import com.example.global_exceptions.Exception;
import com.example.global_exceptions.dto.ErrorDto;

import java.util.List;

public abstract class InvalidInputExceptionList extends Exception {

    abstract List<ErrorDto> getErrorDtoList();
}
