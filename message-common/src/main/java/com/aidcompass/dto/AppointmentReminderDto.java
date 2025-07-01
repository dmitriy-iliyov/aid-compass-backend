package com.aidcompass.dto;

public record AppointmentReminderDto(
     UserDto customer,
     AppointmentDto appointment
) { }