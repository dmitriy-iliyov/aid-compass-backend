package com.example.recovery;

import java.util.Optional;
import java.util.UUID;

public interface PasswordRecoveryRepository {

    void save(UUID token, String recoveryResource);

    Optional<String> findAndDeleteByToken(UUID token);
}
