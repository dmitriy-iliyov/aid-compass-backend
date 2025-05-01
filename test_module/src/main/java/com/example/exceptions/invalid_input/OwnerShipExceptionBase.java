package com.example.exceptions.invalid_input;

import com.example.global_exceptions.BaseInvalidInputException;
import com.example.global_exceptions.dto.ErrorDto;

public class OwnerShipExceptionBase extends BaseInvalidInputException {

    private ErrorDto errorDto;


    public OwnerShipExceptionBase(ErrorDto error) {
        super();
        this.errorDto = error;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
