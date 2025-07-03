package com.aidcompass.jurist.models.dto;

import com.aidcompass.detail.models.PublicDetailResponseDto;

public record FullPublicJuristResponseDto(
    PublicJuristResponseDto jurist,

    PublicDetailResponseDto detail
) { }
