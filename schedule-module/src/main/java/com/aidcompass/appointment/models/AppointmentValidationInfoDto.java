package com.aidcompass.appointment.models;

import com.aidcompass.appointment.marker.AppointmentMarker;

import java.util.UUID;

public record AppointmentValidationInfoDto(
        ValidationStatus status,
        UUID customerId,
        AppointmentMarker dto,
        Long intervalId
) {
}
