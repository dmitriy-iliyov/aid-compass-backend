package com.aidcompass.confirmation.repositories;

import java.util.Optional;
import java.util.UUID;


public interface ConfirmationRepository {

    void save(String key, String value, Long ttl);

    Optional<String> findAndDeleteByToken(String key);
}
