package com.aidcompass.exceptions.appointment;

import com.aidcompass.BaseInternalServerException;
import com.aidcompass.dto.ErrorDto;

public class AppointmentTypeConvertException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Convert exception!");
    }
}
