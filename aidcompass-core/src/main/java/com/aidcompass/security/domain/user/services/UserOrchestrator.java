package com.aidcompass.security.domain.user.services;


import com.aidcompass.security.auth.dto.RecoveryRequestDto;
import com.aidcompass.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.security.domain.user.models.dto.UserRegistrationDto;
import com.aidcompass.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.security.domain.user.models.dto.UserUpdateDto;

import java.util.UUID;

public interface UserOrchestrator {

    void save(UserRegistrationDto dto);

    void confirmByEmail(String email);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    boolean existsById(UUID id);

    SystemUserDto systemFindById(UUID id);

    UserResponseDto findById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto updateDto);

    void recoverPasswordByEmail(RecoveryRequestDto recoveryRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
