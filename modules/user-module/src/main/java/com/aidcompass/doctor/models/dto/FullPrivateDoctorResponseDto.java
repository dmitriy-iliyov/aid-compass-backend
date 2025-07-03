package com.aidcompass.doctor.models.dto;


import com.aidcompass.detail.models.PrivateDetailResponseDto;

public record FullPrivateDoctorResponseDto(
    PrivateDoctorResponseDto doctor,

    PrivateDetailResponseDto detail
) { }
