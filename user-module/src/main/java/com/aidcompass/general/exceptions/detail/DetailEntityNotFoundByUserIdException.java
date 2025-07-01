package com.aidcompass.general.exceptions.detail;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class DetailEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("detail", "Detail not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
