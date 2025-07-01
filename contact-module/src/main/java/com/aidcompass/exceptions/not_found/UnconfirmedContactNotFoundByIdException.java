package com.aidcompass.exceptions.not_found;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class UnconfirmedContactNotFoundByIdException extends BaseNotFoundException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("contact", "Not found!");
    }
}
