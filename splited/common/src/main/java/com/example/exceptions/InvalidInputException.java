package com.example.exceptions;

import com.example.exceptions.dto.ErrorDto;
import lombok.Getter;

@Getter
public class InvalidInputException extends Exception {

    private final static String MESSAGE = "Invalid input!";
    private final ErrorDto errorDto = new ErrorDto("input", MESSAGE);

    public InvalidInputException() {
        super(MESSAGE);
    }

    public InvalidInputException(String MESSAGE) {
        super(MESSAGE);
    }

}
