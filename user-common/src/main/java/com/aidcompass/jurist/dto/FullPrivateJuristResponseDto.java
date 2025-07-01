package com.aidcompass.jurist.dto;

import com.aidcompass.detail.PrivateDetailResponseDto;

public record FullPrivateJuristResponseDto(
    PrivateJuristResponseDto jurist,

    PrivateDetailResponseDto detail
) { }
