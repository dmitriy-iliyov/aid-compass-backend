package com.aidcompass.work_interval.validation.duration;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;

import java.util.Set;
import java.util.UUID;

public interface DurationValidator {
    void validateWorkIntervalDuration(UUID ownerId, WorkIntervalMarker dto);

    void validateWorkIntervalDuration(UUID ownerId, Set<WorkIntervalMarker> dtoSet);
}
