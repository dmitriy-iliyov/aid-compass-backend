package com.aidcompass.contact.exceptions.invalid_input;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class DoubleContactIdException extends BaseInvalidInputException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
