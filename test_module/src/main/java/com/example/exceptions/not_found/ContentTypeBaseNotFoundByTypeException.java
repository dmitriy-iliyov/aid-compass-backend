package com.example.exceptions.not_found;

import com.example.global_exceptions.BaseNotFoundException;
import com.example.global_exceptions.dto.ErrorDto;

public class ContentTypeBaseNotFoundByTypeException extends BaseNotFoundException {

    private ErrorDto errorDto;


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
