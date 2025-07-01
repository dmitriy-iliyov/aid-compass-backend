package com.aidcompass.appointment.dto;

public record StatusFilter(
    boolean scheduled,
    boolean canceled,
    boolean completed
) { }
