package com.aidcompass.client;

import java.util.UUID;

public interface AuthService {
    boolean existsById(UUID ownerId);
}
