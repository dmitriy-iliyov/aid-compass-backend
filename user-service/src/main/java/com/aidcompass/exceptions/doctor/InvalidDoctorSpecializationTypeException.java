package com.aidcompass.exceptions.doctor;

import com.aidcompass.global_exceptions.BaseInvalidInputException;
import com.aidcompass.global_exceptions.dto.ErrorDto;

public class InvalidDoctorSpecializationTypeException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return null;
    }
}
