package com.aidcompass.doctor.models.dto.doctor;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.profile_status.models.ProfileStatus;
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

        List<DoctorSpecialization> specializations,

        @JsonProperty("profile_status")
        ProfileStatus profileStatus
) { }
