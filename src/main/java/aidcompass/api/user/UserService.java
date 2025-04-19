package aidcompass.api.user;

import aidcompass.api.security.models.Role;
import aidcompass.api.user.models.UserEntity;
import aidcompass.api.user.models.dto.UserRegistrationDto;
import aidcompass.api.user.models.dto.UserResponseDto;
import aidcompass.api.user.models.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    void save(UserRegistrationDto customerRegistrationDto);

    void confirmByEmail(String email);

    UserUpdateDto mapToUpdateDto(UserRegistrationDto userRegistrationDto);

    void update(UserUpdateDto userUpdateDto);

    UserEntity systemUpdate(Long id, Role role);

    boolean existingById(Long id);

    UserResponseDto findById(Long id);

    List<UserResponseDto> findAll();

    void deleteByEmail(String email);
}
