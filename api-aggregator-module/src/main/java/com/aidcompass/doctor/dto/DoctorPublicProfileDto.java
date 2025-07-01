package com.aidcompass.doctor.dto;

import com.aidcompass.dto.PublicContactResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorPublicProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor_profile")
        FullPublicDoctorResponseDto fullDoctor,

        @JsonProperty("contacts")
        List<PublicContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }