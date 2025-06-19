package com.aidcompass.exceptions;

import com.aidcompass.models.BaseInvalidInputException;
import com.aidcompass.models.dto.ErrorDto;

public class UserAlreadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("user", "User with this id already exists!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
