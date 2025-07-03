package com.aidcompass.jurist.models.dto;


import com.aidcompass.detail.models.PrivateDetailResponseDto;

public record FullPrivateJuristResponseDto(
    PrivateJuristResponseDto jurist,

    PrivateDetailResponseDto detail
) { }
