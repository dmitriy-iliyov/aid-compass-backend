package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class DetailEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("detail", "Detail not found!");


    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
