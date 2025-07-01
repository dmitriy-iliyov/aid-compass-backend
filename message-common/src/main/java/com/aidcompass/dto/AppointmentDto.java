package com.aidcompass.dto;

import java.time.LocalDate;

public record AppointmentDto(
        String type,
        LocalDate date,
        String description
) { }