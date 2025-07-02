package com.aidcompass.contact.exceptions.not_found;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class UnconfirmedContactNotFoundByIdException extends BaseNotFoundException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("contact", "Not found!");
    }
}
