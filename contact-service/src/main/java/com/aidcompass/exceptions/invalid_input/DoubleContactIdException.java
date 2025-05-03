package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class DoubleContactIdException extends BaseInvalidInputException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
