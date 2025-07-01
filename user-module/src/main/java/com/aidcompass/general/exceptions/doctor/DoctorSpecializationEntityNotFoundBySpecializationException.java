package com.aidcompass.general.exceptions.doctor;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class DoctorSpecializationEntityNotFoundBySpecializationException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
