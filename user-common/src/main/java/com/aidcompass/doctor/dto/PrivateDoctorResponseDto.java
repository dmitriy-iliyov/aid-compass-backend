package com.aidcompass.doctor.dto;

import com.aidcompass.doctor.specialization.DoctorSpecialization;
import com.aidcompass.dto.BasePrivateVolunteerDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateDoctorResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        List<DoctorSpecialization> specializations
) { }
