package com.aidcompass.security.domain.user.services;

import com.aidcompass.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.security.domain.user.models.dto.UserRegistrationDto;

import java.util.UUID;

public interface UnconfirmedUserService {

    void save(UUID id, UserRegistrationDto dto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email, Long emailId);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
