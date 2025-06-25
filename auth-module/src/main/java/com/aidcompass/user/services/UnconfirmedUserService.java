package com.aidcompass.user.services;

import com.aidcompass.base_dto.user.SystemUserDto;
import com.aidcompass.base_dto.user.UserRegistrationDto;

import java.util.UUID;

public interface UnconfirmedUserService {

    void save(UUID id, UserRegistrationDto dto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email, Long emailId);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
