package com.aidcompass.work_interval.validation.overlaps;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.models.overlaps.ValidationInfoDto;

import java.util.UUID;

public interface OverlapsValidator {
    ValidationInfoDto checkForOverlaps(UUID ownerId, WorkIntervalMarker newInterval);
}
