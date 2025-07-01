package com.aidcompass.jurist.dto;

import com.aidcompass.dto.BasePrivateVolunteerDto;
import com.aidcompass.jurist.specialization.JuristSpecialization;
import com.aidcompass.jurist.type.JuristType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateJuristResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        JuristType type,

        List<JuristSpecialization> specializations

) { }
