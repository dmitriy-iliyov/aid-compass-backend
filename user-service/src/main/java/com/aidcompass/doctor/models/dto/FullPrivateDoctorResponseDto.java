package com.aidcompass.doctor.models.dto;

import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FullPrivateDoctorResponseDto(
    PrivateDoctorResponseDto doctor,

    @JsonProperty("detail")
    PrivateDetailResponseDto detail
) { }
