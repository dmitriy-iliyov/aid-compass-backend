package com.example.rest_clients.user;

import com.example.rest_clients.user.dto.UserResponseDto;
import com.example.rest_clients.user.dto.UserUpdateDto;

import java.util.UUID;

public interface UserService {
    UserResponseDto findById(UUID id);

    void update(UUID id, UserUpdateDto user);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
