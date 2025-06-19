package com.aidcompass.jurist.dto;

import com.aidcompass.contact.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import com.aidcompass.jurist.models.dto.FullPrivateJuristResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JuristPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist_profile")
        FullPrivateJuristResponseDto fullJurist,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
