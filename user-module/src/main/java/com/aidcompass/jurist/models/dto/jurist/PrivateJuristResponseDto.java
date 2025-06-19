package com.aidcompass.jurist.models.dto.jurist;

import com.aidcompass.base_dto.BasePrivateVolunteerDto;
import com.aidcompass.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.jurist.specialization.models.JuristType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateJuristResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        JuristType type,

        List<JuristSpecialization> specializations

) { }
