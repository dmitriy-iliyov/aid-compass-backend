package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseInternalServiceException;
import com.aidcompass.models.dto.ErrorDto;

public class AppointmentStatusConvertException extends BaseInternalServiceException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status", "Convert exception!");
    }
}