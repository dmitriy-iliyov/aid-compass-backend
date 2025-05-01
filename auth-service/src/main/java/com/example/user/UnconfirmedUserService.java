package com.example.user;


import com.example.user.models.dto.SystemUserDto;
import com.example.user.models.dto.UserRegistrationDto;

public interface UnconfirmedUserService {

    void save(UserRegistrationDto userRegistrationDto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);
}
