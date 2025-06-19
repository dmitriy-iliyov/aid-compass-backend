package com.aidcompass.exceptions;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class ProfileStatusEntityNotFoundByStatusException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_status", "ProfileStatus not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
