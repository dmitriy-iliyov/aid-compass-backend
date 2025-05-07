package com.aidcompass.user.services;


import com.aidcompass.user.models.dto.SystemUserDto;
import com.aidcompass.user.models.dto.UserRegistrationDto;

import java.util.UUID;

public interface UnconfirmedUserService {

    void save(UUID id, UserRegistrationDto dto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
