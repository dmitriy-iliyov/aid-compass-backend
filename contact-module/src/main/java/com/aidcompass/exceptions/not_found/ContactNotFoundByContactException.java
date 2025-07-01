package com.aidcompass.exceptions.not_found;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ContactNotFoundByContactException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
