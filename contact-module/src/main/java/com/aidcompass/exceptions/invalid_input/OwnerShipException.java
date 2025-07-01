package com.aidcompass.exceptions.invalid_input;

import com.aidcompass.BaseInvalidInputException;
import com.aidcompass.dto.ErrorDto;

public class OwnerShipException extends BaseInvalidInputException {

    private ErrorDto errorDto;


    public OwnerShipException(ErrorDto error) {
        super();
        this.errorDto = error;
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
