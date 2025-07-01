package com.aidcompass.jurist.dto;

import com.aidcompass.detail.PublicDetailResponseDto;

public record FullPublicJuristResponseDto(
    PublicJuristResponseDto jurist,

    PublicDetailResponseDto detail
) { }
