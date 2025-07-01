package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInternalServerException;
import com.aidcompass.dto.ErrorDto;

public class AppointmentStatusConvertException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status", "Convert exception!");
    }
}