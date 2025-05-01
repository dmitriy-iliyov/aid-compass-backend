package com.example.exceptions;

import com.aidcompass.common.global_exceptions.Exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends Exception {

    private final static String MESSAGE = "Not found";
    private final String code = "";

    public NotFoundException() {
        super(MESSAGE);
    }

    public NotFoundException(String message) {
        super(MESSAGE + ": " + message);
    }

}
