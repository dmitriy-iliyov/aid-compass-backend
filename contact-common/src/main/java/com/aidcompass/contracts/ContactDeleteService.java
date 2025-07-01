package com.aidcompass.contracts;

import java.util.UUID;

public interface ContactDeleteService {
    void deleteById(UUID ownerId, Long id);
    void deleteAll(UUID ownerId);
}
