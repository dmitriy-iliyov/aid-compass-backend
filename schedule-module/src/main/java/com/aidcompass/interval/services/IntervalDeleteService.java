package com.aidcompass.interval.services;

import java.util.UUID;

public interface IntervalDeleteService {
    void deleteAllByOwnerId(UUID ownerId);
}
