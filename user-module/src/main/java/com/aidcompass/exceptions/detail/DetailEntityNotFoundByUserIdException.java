package com.aidcompass.exceptions.detail;

import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.models.dto.ErrorDto;

public class DetailEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("detail", "Detail not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
