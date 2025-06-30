package com.aidcompass.dto;

public record ReminderAppointmentDto(
     UserDto customer,
     AppointmentDto appointment
) { }