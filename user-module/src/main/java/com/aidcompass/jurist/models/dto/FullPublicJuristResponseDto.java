package com.aidcompass.jurist.models.dto;

import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import com.aidcompass.jurist.models.dto.jurist.PublicJuristResponseDto;

public record FullPublicJuristResponseDto(
    PublicJuristResponseDto jurist,

    PublicDetailResponseDto detail
) { }
