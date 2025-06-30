package com.aidcompass.dto;

public record AppointmentDto(
        String date,
        String type,
        String description
) { }