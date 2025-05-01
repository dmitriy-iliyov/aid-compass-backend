package com.aidcompass.auth.user;

import com.aidcompass.auth.user.models.dto.SystemUserDto;
import com.aidcompass.auth.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.auth.user.models.dto.UserResponseDto;
import com.aidcompass.auth.user.models.dto.UserUpdateDto;

import java.util.UUID;

public interface UserService {

    void save(SystemUserDto systemUserDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    UserResponseDto update(SystemUserUpdateDto systemUserUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void confirmByEmail(String email);

    SystemUserDto systemFindByEmail(String mail);

    SystemUserDto systemFindById(UUID id);

//    PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);

    SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto);

    UserResponseDto findById(UUID id);
}
