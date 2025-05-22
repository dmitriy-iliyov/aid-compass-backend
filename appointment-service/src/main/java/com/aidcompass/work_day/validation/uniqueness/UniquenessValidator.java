package com.aidcompass.work_day.validation.uniqueness;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;

import java.util.UUID;

public interface UniquenessValidator {
    void checkUniqueness(UUID ownerId, WorkDayCreateDto dto);

    void checkUniqueness(UUID ownerId, WorkDayUpdateDto dto);
}
