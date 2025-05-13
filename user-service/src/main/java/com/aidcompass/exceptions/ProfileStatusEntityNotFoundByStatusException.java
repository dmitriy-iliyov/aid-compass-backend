package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class ProfileStatusEntityNotFoundByStatusException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_status", "ProfileStatus not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
