package com.aidcompass.contact.exceptions.not_found;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class ContactTypeNotFoundByTypeException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
