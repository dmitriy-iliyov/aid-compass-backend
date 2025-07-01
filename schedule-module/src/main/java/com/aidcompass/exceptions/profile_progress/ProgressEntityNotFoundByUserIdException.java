package com.aidcompass.exceptions.profile_progress;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ProgressEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_progress", "Profile progress not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
