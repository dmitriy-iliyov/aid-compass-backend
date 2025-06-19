package com.aidcompass.appointment.models.dto;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.interval.models.overlaps.ValidationStatus;

import java.util.UUID;

public record AppointmentValidationInfoDto(
        ValidationStatus status,
        UUID customerId,
        AppointmentMarker dto,
        Long intervalId
) {
}
