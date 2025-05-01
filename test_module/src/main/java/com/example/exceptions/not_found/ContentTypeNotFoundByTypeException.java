package com.example.exceptions.not_found;

import com.aidcompass.common.global_exceptions.BaseNotFoundException;
import com.aidcompass.common.global_exceptions.dto.ErrorDto;

public class ContentTypeNotFoundByTypeException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
