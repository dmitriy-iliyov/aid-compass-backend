package com.aidcompass.jurist.dto;

import com.aidcompass.enums.gender.Gender;
import com.aidcompass.jurist.specialization.JuristSpecialization;
import com.aidcompass.jurist.type.JuristType;
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
