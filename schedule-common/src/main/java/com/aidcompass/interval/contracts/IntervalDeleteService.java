package com.aidcompass.interval.contracts;

import java.util.UUID;

public interface IntervalDeleteService {
    void deleteAllByOwnerId(UUID ownerId);
}
