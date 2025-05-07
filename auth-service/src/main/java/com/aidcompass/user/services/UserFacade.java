package com.aidcompass.user.services;


import com.aidcompass.clients.RecoveryRequestDto;
import com.aidcompass.user.models.dto.SystemUserDto;
import com.aidcompass.user.models.dto.UserRegistrationDto;
import com.aidcompass.user.models.dto.UserResponseDto;
import com.aidcompass.user.models.dto.UserUpdateDto;

import java.util.UUID;

public interface UserFacade {

    void save(UserRegistrationDto dto);

    void confirmByEmail(String email);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    SystemUserDto systemFindById(UUID id);

    UserResponseDto findById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto updateDto);

    void recoverPasswordByEmail(RecoveryRequestDto recoveryRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
