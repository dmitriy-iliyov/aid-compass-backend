package com.aidcompass.exceptions.not_found;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class UserNotFoundByEmailException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("email", "User not found by email!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
