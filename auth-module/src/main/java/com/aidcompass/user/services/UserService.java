package com.aidcompass.user.services;

import com.aidcompass.dto.user.SystemUserDto;
import com.aidcompass.dto.user.UserResponseDto;
import com.aidcompass.dto.user.UserUpdateDto;
import com.aidcompass.enums.Authority;
import com.aidcompass.user.models.DefaultUserDetails;
import com.aidcompass.user.models.SystemUserUpdateDto;

import java.util.UUID;

public interface UserService {

    void save(SystemUserDto systemUserDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByIdAndAuthority(UUID id, Authority authority);

    UserResponseDto update(SystemUserUpdateDto systemUserUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void confirmByEmail(String email, Long emailId);

    SystemUserDto systemFindByEmail(String mail);

    DefaultUserDetails changeAuthorityById(UUID id, Authority authority);

    SystemUserDto systemFindById(UUID id);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);

    SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto);

    UserResponseDto findById(UUID id);
}
