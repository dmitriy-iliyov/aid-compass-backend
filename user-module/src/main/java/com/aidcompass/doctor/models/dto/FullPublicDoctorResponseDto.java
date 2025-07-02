package com.aidcompass.doctor.models.dto;


import com.aidcompass.detail.models.PublicDetailResponseDto;

public record FullPublicDoctorResponseDto(
    PublicDoctorResponseDto doctor,

    PublicDetailResponseDto detail
) { }
