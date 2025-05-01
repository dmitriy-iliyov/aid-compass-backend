package com.example.global_exceptions;

import com.example.global_exceptions.dto.ErrorDto;

public abstract class BaseInvalidInputException extends Exception {

    private final static String MESSAGE = "Invalid input!";

    abstract public ErrorDto getErrorDto();

}
