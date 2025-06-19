package com.aidcompass.jurist.models.dto;

import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.jurist.models.dto.jurist.PrivateJuristResponseDto;

public record FullPrivateJuristResponseDto(
    PrivateJuristResponseDto jurist,

    PrivateDetailResponseDto detail
) { }
