package com.aidcompass.security.exceptions.not_found;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class UserNotFoundByEmailException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("email", "User not found by email!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
