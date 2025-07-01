package com.aidcompass.dto;

public record AppointmentCanceledDto(
        UserDto customer,
        UserDto volunteer,
        AppointmentDto appointment
) { }
