package com.aidcompass.auth.user;


import com.aidcompass.auth.user.models.dto.SystemUserDto;
import com.aidcompass.auth.user.models.dto.UserRegistrationDto;

public interface UnconfirmedUserService {

    void save(UserRegistrationDto userRegistrationDto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
