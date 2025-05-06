package com.aidcompass.global_exceptions;

import com.aidcompass.global_exceptions.dto.ErrorDto;

public abstract class Exception extends RuntimeException {


    public Exception() {

    }

    public Exception(String message) {
        super(message);
    }

    abstract public ErrorDto getErrorDto();
}
