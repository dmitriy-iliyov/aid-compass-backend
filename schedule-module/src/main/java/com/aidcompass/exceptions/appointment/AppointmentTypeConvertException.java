package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInternalServiceException;
import com.aidcompass.dto.ErrorDto;

public class AppointmentTypeConvertException extends BaseInternalServiceException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Convert exception!");
    }
}
