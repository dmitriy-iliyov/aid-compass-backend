package com.example.client;

import com.example.client.dto.UserResponseDto;
import com.example.client.dto.UserUpdateDto;

import java.util.UUID;

public interface AuthService {
    UserResponseDto findById(UUID id);

    void update(UUID id, UserUpdateDto user);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);

    boolean existsById(UUID ownerId);
}
