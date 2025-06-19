package com.aidcompass.interval.validation.ownership;

import java.util.UUID;

public interface IntervalOwnershipValidator {
    void validate(UUID ownerId, Long id);
}
