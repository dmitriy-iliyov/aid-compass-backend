package com.aidcompass.api.doctor.dto;


import com.aidcompass.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor_profile")
        FullPrivateDoctorResponseDto fullDoctor,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
