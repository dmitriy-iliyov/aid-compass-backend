package com.aidcompass.exceptions;


import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class ContactsBaseNotFoundException extends BaseNotFoundException {

    private ErrorDto errorDto;


    public ContactsBaseNotFoundException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
