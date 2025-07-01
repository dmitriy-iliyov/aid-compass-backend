package com.aidcompass.exceptions.not_found;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ContactProgressEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("progress_entity", "Not found by user id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
