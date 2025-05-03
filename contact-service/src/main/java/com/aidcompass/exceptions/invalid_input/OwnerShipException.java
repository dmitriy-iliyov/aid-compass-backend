package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class OwnerShipException extends BaseInvalidInputException {

    private ErrorDto errorDto;


    public OwnerShipException(ErrorDto error) {
        super();
        this.errorDto = error;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
