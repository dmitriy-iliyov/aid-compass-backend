package com.aidcompass.dto;

public record ScheduledAppointmentDto(
        UserDto customer,
        String volunteerType,
        UserDto volunteer,
        AppointmentDto appointment
) { }
