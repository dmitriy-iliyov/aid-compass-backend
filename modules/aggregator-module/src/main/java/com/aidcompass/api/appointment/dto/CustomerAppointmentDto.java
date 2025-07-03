package com.aidcompass.api.appointment.dto;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerAppointmentDto(
        AppointmentResponseDto appointment,

        @JsonProperty("avatar_url")
        String avatarUrl,

        PublicVolunteerDto volunteer
) { }