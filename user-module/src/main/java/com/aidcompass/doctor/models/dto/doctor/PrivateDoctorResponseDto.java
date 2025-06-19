package com.aidcompass.doctor.models.dto.doctor;

import com.aidcompass.base_dto.BasePrivateVolunteerDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateDoctorResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        List<DoctorSpecialization> specializations
) { }
