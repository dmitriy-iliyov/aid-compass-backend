package com.aidcompass.common.global_exceptions;

import com.aidcompass.common.global_exceptions.dto.ErrorDto;

public abstract class BaseInvalidInputException extends Exception {

    private final static String MESSAGE = "Invalid input!";

    abstract public ErrorDto getErrorDto();

}
