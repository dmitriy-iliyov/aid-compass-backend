package com.aidcompass.contact.exceptions.invalid_input;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

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
