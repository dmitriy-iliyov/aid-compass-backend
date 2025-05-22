package com.aidcompass.work_interval.models.overlaps;

import com.aidcompass.work_interval.models.WorkIntervalMarker;

public record ValidationInfoDto(
        ValidationStatus status,
        WorkIntervalMarker dto,
        Long id
) { }
