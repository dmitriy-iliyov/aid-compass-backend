package com.aidcompass.general.exceptions;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ProfileStatusEntityNotFoundByStatusException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_status", "ProfileStatus not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
