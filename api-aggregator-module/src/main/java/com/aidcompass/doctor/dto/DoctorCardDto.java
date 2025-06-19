package com.aidcompass.doctor.dto;

import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorCardDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor")
        PublicDoctorResponseDto fullDoctor,

        @JsonProperty("nearest_interval")
        NearestIntervalDto nearestInterval,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
