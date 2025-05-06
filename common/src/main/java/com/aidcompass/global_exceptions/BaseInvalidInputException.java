package com.aidcompass.global_exceptions;

import com.aidcompass.global_exceptions.dto.ErrorDto;

public abstract class BaseInvalidInputException extends Exception {

    private final static String MESSAGE = "Invalid input!";
}
