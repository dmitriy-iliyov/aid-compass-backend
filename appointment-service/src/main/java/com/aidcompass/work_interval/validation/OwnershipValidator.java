package com.aidcompass.work_interval.validation;

import java.util.List;
import java.util.UUID;

public interface OwnershipValidator {
    void assertOwnership(UUID ownerId, Long id);

    void assertOwnership(UUID ownerId, List<Long> ids);
}
