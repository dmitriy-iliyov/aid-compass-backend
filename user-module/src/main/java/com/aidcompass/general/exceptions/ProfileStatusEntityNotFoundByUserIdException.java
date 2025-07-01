package com.aidcompass.general.exceptions;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class ProfileStatusEntityNotFoundByUserIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
