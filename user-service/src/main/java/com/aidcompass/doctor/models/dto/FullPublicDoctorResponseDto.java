package com.aidcompass.doctor.models.dto;

import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FullPublicDoctorResponseDto(
    PublicDoctorResponseDto doctor,

    @JsonProperty("detail")
    PublicDetailResponseDto detail
) { }
