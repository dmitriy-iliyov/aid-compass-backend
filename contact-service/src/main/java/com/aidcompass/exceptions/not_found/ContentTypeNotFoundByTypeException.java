package com.aidcompass.exceptions.not_found;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class ContentTypeNotFoundByTypeException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
