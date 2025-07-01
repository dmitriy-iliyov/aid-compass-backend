package com.aidcompass.doctor.dto;

import com.aidcompass.detail.PrivateDetailResponseDto;

public record FullPrivateDoctorResponseDto(
    PrivateDoctorResponseDto doctor,

    PrivateDetailResponseDto detail
) { }
