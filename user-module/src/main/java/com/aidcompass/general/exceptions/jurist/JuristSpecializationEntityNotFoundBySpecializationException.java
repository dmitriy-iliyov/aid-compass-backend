package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class JuristSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
