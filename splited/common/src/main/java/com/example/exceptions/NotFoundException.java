package com.example.exceptions;

import com.example.exceptions.dto.ErrorDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends Exception {

    private final static String MESSAGE = "Not found";
    private final String code = "";
    @Getter
    private final ErrorDto errorDto = new ErrorDto("input", MESSAGE);


    public NotFoundException() {
        super(MESSAGE);
    }

    public NotFoundException(String message) {
        super(MESSAGE + ": " + message);
    }

}
