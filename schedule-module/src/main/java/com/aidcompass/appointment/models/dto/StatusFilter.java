package com.aidcompass.appointment.models.dto;

public record StatusFilter(
    boolean scheduled,
    boolean canceled,
    boolean completed
) { }
