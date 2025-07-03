package com.aidcompass.general.exceptions.jurist;

import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class JuristSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
