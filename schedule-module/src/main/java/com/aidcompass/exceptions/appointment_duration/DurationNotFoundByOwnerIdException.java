package com.aidcompass.exceptions.appointment_duration;

import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;

public class DurationNotFoundByOwnerIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("duration", "Appointment duration not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
