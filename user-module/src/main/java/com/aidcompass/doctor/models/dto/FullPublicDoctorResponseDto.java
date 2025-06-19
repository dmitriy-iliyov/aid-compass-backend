package com.aidcompass.doctor.models.dto;

import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;

public record FullPublicDoctorResponseDto(
    PublicDoctorResponseDto doctor,

    PublicDetailResponseDto detail
) { }
