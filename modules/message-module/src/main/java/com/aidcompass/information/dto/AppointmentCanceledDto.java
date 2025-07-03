package com.aidcompass.information.dto;

public record AppointmentCanceledDto(
        UserDto customer,
        UserDto volunteer,
        AppointmentDto appointment
) { }
