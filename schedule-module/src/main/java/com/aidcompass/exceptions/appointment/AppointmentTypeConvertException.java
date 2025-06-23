package com.aidcompass.exceptions.appointment;

import com.aidcompass.models.BaseInternalServiceException;
import com.aidcompass.models.dto.ErrorDto;

public class AppointmentTypeConvertException extends BaseInternalServiceException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Convert exception!");
    }
}
