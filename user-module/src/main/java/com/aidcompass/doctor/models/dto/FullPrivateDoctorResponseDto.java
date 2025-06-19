package com.aidcompass.doctor.models.dto;

import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;

public record FullPrivateDoctorResponseDto(
    PrivateDoctorResponseDto doctor,

    PrivateDetailResponseDto detail
) { }
