package com.aidcompass.interval.models.overlaps;

import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;

import java.util.List;

public record ValidationInfoDto(
        ValidationStatus status,
        SystemIntervalCreatedDto dto,
        List<Long> toDeleteIdList
) { }
