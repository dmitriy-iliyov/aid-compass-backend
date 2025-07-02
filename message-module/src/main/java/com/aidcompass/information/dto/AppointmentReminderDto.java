package com.aidcompass.information.dto;

public record AppointmentReminderDto(
     UserDto customer,
     AppointmentDto appointment
) { }