package com.aidcompass.contracts;


import com.aidcompass.base_dto.*;
import com.aidcompass.enums.Authority;

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
