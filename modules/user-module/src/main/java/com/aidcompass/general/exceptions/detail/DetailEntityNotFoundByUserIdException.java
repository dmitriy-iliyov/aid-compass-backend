package com.aidcompass.general.exceptions.detail;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class DetailEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("detail", "Detail not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
