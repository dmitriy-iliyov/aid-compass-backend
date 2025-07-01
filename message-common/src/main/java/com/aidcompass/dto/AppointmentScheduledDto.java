package com.aidcompass.dto;

public record AppointmentScheduledDto(
        UserDto customer,
        String volunteerType,
        UserDto volunteer,
        AppointmentDto appointment
) { }
