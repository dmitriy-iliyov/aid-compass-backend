package com.example.exceptions.invalid_input;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class DoubleContactIdExceptionBase extends BaseInvalidInputException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
