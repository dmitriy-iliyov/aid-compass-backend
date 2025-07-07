package com.aidcompass.appointment.models.dto;

import com.aidcompass.appointment.models.enums.ValidationStatus;
import com.aidcompass.appointment.models.marker.AppointmentMarker;

import java.util.UUID;

public record AppointmentValidationInfo(
        ValidationStatus status,
        UUID customerId,
        AppointmentMarker dto,
        Long intervalId
) { }