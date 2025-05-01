package com.aidcompass.common.global_exceptions;

import com.aidcompass.common.global_exceptions.dto.ErrorDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseNotFoundException extends Exception {

    private final static String MESSAGE = "Not found";
    private final String code = "";


    public BaseNotFoundException() {
        super(MESSAGE);
    }

    public BaseNotFoundException(String message) {
        super(MESSAGE + ": " + message);
    }


    abstract public ErrorDto getErrorDto();

}
