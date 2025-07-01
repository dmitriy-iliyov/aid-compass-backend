package com.aidcompass.jurist.dto;

import com.aidcompass.dto.PublicContactResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JuristPublicProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist_profile")
        FullPublicJuristResponseDto fullJurist,

        @JsonProperty("contacts")
        List<PublicContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
