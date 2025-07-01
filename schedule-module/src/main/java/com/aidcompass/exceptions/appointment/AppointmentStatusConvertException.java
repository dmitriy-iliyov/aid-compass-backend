package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInternalServiceException;
import com.aidcompass.dto.ErrorDto;

public class AppointmentStatusConvertException extends BaseInternalServiceException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status", "Convert exception!");
    }
}