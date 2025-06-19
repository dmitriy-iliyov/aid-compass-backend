package com.aidcompass.jurist.dto;

import com.aidcompass.jurist.models.dto.jurist.PublicJuristResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JuristCardDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist")
        PublicJuristResponseDto fullJurist,

        @JsonProperty("nearest_interval")
        NearestIntervalDto nearestInterval,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
