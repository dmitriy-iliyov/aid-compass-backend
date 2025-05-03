package com.aidcompass.user.services;


import com.aidcompass.user.models.dto.SystemUserDto;
import com.aidcompass.user.models.dto.UserRegistrationDto;

public interface UnconfirmedUserService {

    void save(UserRegistrationDto userRegistrationDto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
