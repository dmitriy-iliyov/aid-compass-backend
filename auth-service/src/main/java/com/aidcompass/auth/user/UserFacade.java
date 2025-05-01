package com.aidcompass.auth.user;


import com.aidcompass.auth.clients.RecoveryRequestDto;
import com.aidcompass.auth.user.models.dto.SystemUserDto;
import com.aidcompass.auth.user.models.dto.UserRegistrationDto;
import com.aidcompass.auth.user.models.dto.UserResponseDto;
import com.aidcompass.auth.user.models.dto.UserUpdateDto;

import java.util.UUID;

public interface UserFacade {

    void save(UserRegistrationDto userRegistrationDto);

    void confirmByEmail(String email);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    SystemUserDto systemFindById(UUID id);

    UserResponseDto findById(UUID id);

    UserResponseDto update(UUID id, UserUpdateDto userUpdateDto);

    void recoverPasswordByEmail(RecoveryRequestDto recoveryRequestDto);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);
}
