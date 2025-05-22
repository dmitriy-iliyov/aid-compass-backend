package com.aidcompass.work_interval.validation.ownership;

import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OwnershipValidator {
    void assertOwnership(UUID ownerId, Long id);

    void assertOwnership(UUID ownerId, Set<WorkIntervalUpdateDto> dtoSet);
}
