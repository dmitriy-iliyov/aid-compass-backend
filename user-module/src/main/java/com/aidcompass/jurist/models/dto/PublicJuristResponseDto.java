package com.aidcompass.jurist.models.dto;

import com.aidcompass.gender.Gender;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record PublicJuristResponseDto(
        UUID id,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        JuristType type,

        List<JuristSpecialization> specializations,

        @JsonProperty("specialization_detail")
        String specializationDetail,

        @JsonProperty("working_experience")
        Integer workingExperience,

        Gender gender
) { }
