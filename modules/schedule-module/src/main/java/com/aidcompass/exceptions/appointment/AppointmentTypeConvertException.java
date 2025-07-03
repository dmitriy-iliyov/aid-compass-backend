package com.aidcompass.exceptions.appointment;

import com.aidcompass.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class AppointmentTypeConvertException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("type", "Convert exception!");
    }
}
