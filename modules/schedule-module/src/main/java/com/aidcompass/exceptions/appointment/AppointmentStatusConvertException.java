package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class AppointmentStatusConvertException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status", "Convert exception!");
    }
}