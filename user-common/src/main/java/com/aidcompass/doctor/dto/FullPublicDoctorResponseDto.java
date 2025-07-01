package com.aidcompass.doctor.dto;


import com.aidcompass.detail.PublicDetailResponseDto;

public record FullPublicDoctorResponseDto(
    PublicDoctorResponseDto doctor,

    PublicDetailResponseDto detail
) { }
