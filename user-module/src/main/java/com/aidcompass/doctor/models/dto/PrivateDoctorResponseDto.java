package com.aidcompass.doctor.models.dto;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.general.dto.BasePrivateVolunteerDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateDoctorResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        List<DoctorSpecialization> specializations
) { }
