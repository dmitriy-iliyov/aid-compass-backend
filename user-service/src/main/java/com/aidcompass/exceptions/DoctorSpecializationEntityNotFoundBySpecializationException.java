package com.aidcompass.exceptions;

import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class DoctorSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
