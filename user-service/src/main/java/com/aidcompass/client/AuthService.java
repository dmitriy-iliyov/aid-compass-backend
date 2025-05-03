package com.aidcompass.client;

import com.aidcompass.client.dto.UserResponseDto;
import com.aidcompass.client.dto.UserUpdateDto;

import java.util.UUID;

public interface AuthService {
    UserResponseDto findById(UUID id);

    void update(UUID id, UserUpdateDto user);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);

    boolean existsById(UUID ownerId);
}
