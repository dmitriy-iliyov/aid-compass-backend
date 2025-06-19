package com.aidcompass.pass_recovery;

import java.util.Optional;
import java.util.UUID;

public interface PasswordRecoveryRepository {

    void save(String code, String recoveryResource);

    Optional<String> findAndDeleteByToken(String code);
}
