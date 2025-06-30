package com.aidcompass.dto;

public record CanceledAppointmentDto(
        UserDto customer,
        UserDto volunteer,
        AppointmentDto appointment
) { }
