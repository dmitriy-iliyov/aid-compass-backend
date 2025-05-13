package com.aidcompass.doctor.models.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record PrivateDoctorResponseDto(
        UUID id,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        @JsonProperty("second_name")
        String lastName,

        List<String> specializations,

        String address,

        @JsonProperty("profile_status")
        String profileStatus
) { }
