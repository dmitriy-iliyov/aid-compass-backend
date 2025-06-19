//package com.aidcompass.user.services;
//
//
//import com.aidcompass.security.core.models.authority.AuthorityService;
//import com.aidcompass.authority.models.Authority;
//import com.aidcompass.security.core.models.authority.models.AuthorityEntity;
//import com.aidcompass.exceptions.illegal_input.IncorrectPasswordException;
//import com.aidcompass.exceptions.not_found.AuthorityNotFoundException;
//import com.aidcompass.exceptions.not_found.UserNotFoundByEmailException;
//import com.aidcompass.exceptions.not_found.UserNotFoundByIdException;
//import com.aidcompass.general.PasswordEncoder;
//import com.aidcompass.user.repositories.UserRepository;
//import com.aidcompass.user.mapper.UserMapper;
//import com.aidcompass.user.models.dto.SystemUserDto;
//import com.aidcompass.user.models.dto.SystemUserUpdateDto;
//import com.aidcompass.user.models.dto.UserResponseDto;
//import com.aidcompass.user.models.dto.UserUpdateDto;
//import com.aidcompass.user.models.UserEntity;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.Instant;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceUnitTests {
//
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    UserMapper userMapper;
//
//    @Mock
//    AuthorityService authorityService;
//
//    @Mock
//    PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    UserServiceImpl userService;
//
//
//    @Test
//    @DisplayName("UT: save() should convert DTO, assign ROLE_USER, and persist user twice")
//    void save_shouldSaveUserEntity() {
//        SystemUserDto systemUserDto = new SystemUserDto(
//                UUID.randomUUID(),
//                "test@email.com",
//                "test_password",
//                List.of(Authority.ROLE_USER),
//                null, null);
//
//        UserEntity mappedEntity = new UserEntity();
//        UserEntity savedEntity = new UserEntity();
//        savedEntity.setAuthorities(new ArrayList<>());
//
//        AuthorityEntity authorityEntity = new AuthorityEntity();
//
//        doReturn(mappedEntity).when(userMapper).toEntity(systemUserDto);
//        doReturn(savedEntity).when(userRepository).save(mappedEntity);
//        doReturn(authorityEntity).when(authorityService).findByAuthority(Authority.ROLE_USER);
//
//        userService.save(systemUserDto);
//
//        verify(userMapper, times(1)).toEntity(systemUserDto);
//        verify(authorityService, times(1)).findByAuthority(Authority.ROLE_USER);
//        verify(userRepository, times(2)).save(any(UserEntity.class));
//        verifyNoMoreInteractions(userMapper, authorityService, userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: existsById() should return true when userRepository.existsById returns true")
//    void existsById_shouldReturnTrue_whenRepositoryReturnsTrue() {
//        UUID id = UUID.randomUUID();
//        when(userRepository.existsById(id)).thenReturn(true);
//
//        boolean result = userService.existsById(id);
//
//        assertTrue(result);
//        verify(userRepository, times(1)).existsById(id);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: existsById() should return false when userRepository.existsById returns false")
//    void existsById_shouldReturnFalse_whenRepositoryReturnsFalse() {
//        UUID id = UUID.randomUUID();
//        when(userRepository.existsById(id)).thenReturn(false);
//
//        boolean result = userService.existsById(id);
//
//        assertFalse(result);
//        verify(userRepository, times(1)).existsById(id);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: existsByEmail() should return true when userRepository.existsByEmail returns true")
//    void existsByEmail_shouldReturnTrue_whenRepositoryReturnsTrue() {
//        String email = "test@example.com";
//        when(userRepository.existsByEmail(email)).thenReturn(true);
//
//        boolean result = userService.existsByEmail(email);
//
//        assertTrue(result);
//        verify(userRepository, times(1)).existsByEmail(email);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: existsByEmail() should return false when userRepository.existsByEmail returns false")
//    void existsByEmail_shouldReturnFalse_whenRepositoryReturnsFalse() {
//        String email = "test@example.com";
//        when(userRepository.existsByEmail(email)).thenReturn(false);
//
//        boolean result = userService.existsByEmail(email);
//
//        assertFalse(result);
//        verify(userRepository, times(1)).existsByEmail(email);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: update() should update user when data is valid")
//    void update() {
//        UUID id = UUID.randomUUID();
//        SystemUserUpdateDto systemUserUpdateDto =
//                new SystemUserUpdateDto(id, "testu@example.com", "test_password", List.of(Authority.ROLE_USER));
//        UserEntity userEntity = new UserEntity();
//        UserResponseDto respDto = new UserResponseDto(id, systemUserUpdateDto.getEmail(), Instant.now());
//        List<AuthorityEntity> authorityEntityList = List.of(new AuthorityEntity());
//
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(id);
//        doNothing().when(userMapper).updateEntityFromDto(systemUserUpdateDto, userEntity);
//        doReturn(authorityEntityList).when(authorityService).toAuthorityEntityList(systemUserUpdateDto.getAuthorities());
//        doReturn(userEntity).when(userRepository).save(userEntity);
//        doReturn(respDto).when(userMapper).toResponseDto(userEntity);
//
//        UserResponseDto dto = userService.update(systemUserUpdateDto);
//
//        assertEquals(dto, respDto);
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, times(1)).updateEntityFromDto(systemUserUpdateDto, userEntity);
//        verify(authorityService, times(1)).toAuthorityEntityList(systemUserUpdateDto.getAuthorities());
//        verify(userRepository, times(1)).save(userEntity);
//        verify(userMapper, times(1)).toResponseDto(userEntity);
//        verifyNoMoreInteractions(userRepository, userMapper, authorityService);
//    }
//
//    @Test
//    @DisplayName("UT: update() should throw UserNotFoundByIdException when user is not found")
//    void update_whenUserNotExist_shouldThrowException() {
//        UUID id = UUID.randomUUID();
//        SystemUserUpdateDto systemUserUpdateDto =
//                new SystemUserUpdateDto(id, "testu@example.com", "test_password", List.of(Authority.ROLE_USER));
//
//        doReturn(Optional.empty()).when(userRepository).findById(id);
//
//        assertThrows(UserNotFoundByIdException.class, () -> userService.update(systemUserUpdateDto));
//
//        verify(userRepository, times(1)).findById(id);
//        verifyNoMoreInteractions(userRepository, userMapper, authorityService);
//    }
//
//    @Test
//    @DisplayName("UT: update() should throw AuthorityNotFoundException when authority list is null")
//    void update_whenAuthorityListIsNull_shouldThrowException() {
//        UUID id = UUID.randomUUID();
//        SystemUserUpdateDto systemUserUpdateDto =
//                new SystemUserUpdateDto(id, "testu@example.com", "test_password", null);
//        UserEntity userEntity = new UserEntity();
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(id);
//        doNothing().when(userMapper).updateEntityFromDto(systemUserUpdateDto, userEntity);
//        doThrow(new AuthorityNotFoundException()).when(authorityService)
//                .toAuthorityEntityList(systemUserUpdateDto.getAuthorities());
//
//        assertThrows(AuthorityNotFoundException.class, () -> userService.update(systemUserUpdateDto));
//
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, times(1)).updateEntityFromDto(systemUserUpdateDto, userEntity);
//        verify(authorityService, times(1)).toAuthorityEntityList(systemUserUpdateDto.getAuthorities());
//        verifyNoMoreInteractions(userRepository, userMapper, authorityService);
//    }
//
//    @Test
//    @DisplayName("UT: update() should update password when user is found")
//    void updatePasswordByEmail_shouldUpdatePassword() {
//        String email = "test@example.com";
//        String rawPassword = "newPassword";
//        String encodedPassword = "encodedPassword";
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(email);
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findByEmail(email);
//        doReturn(encodedPassword).when(passwordEncoder).encode(rawPassword);
//
//        userService.updatePasswordByEmail(email, rawPassword);
//
//        assertEquals(encodedPassword, userEntity.getPassword());
//        verify(userRepository, times(1)).findByEmail(email);
//        verify(passwordEncoder, times(1)).encode(rawPassword);
//        verify(userRepository, times(1)).save(userEntity);
//        verifyNoMoreInteractions(userRepository, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("UT: update() should throw UserNotFoundByEmailException when user not found")
//    void updatePasswordByEmail_whenUserNotFound_shouldThrowException() {
//        String email = "missing@example.com";
//        String password = "password123";
//
//        doReturn(Optional.empty()).when(userRepository).findByEmail(email);
//
//        assertThrows(UserNotFoundByEmailException.class,
//                () -> userService.updatePasswordByEmail(email, password));
//
//        verify(userRepository, times(1)).findByEmail(email);
//        verifyNoMoreInteractions(userRepository, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("UT: confirmUserByEmail() should confirm user by removing ROLE_UNCONFIRMED_USER and adding ROLE_USER")
//    void confirmByEmail_shouldUpdateAuthorityCorrectly() {
//        String email = "test@gmail.com";
//
//        AuthorityEntity userRole = new AuthorityEntity();
//        userRole.setAuthority(Authority.ROLE_USER);
//
//        AuthorityEntity unconfUserRole = new AuthorityEntity();
//        unconfUserRole.setAuthority(Authority.ROLE_UNCONFIRMED_USER);
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(email);
//        userEntity.setAuthorities(new ArrayList<>(List.of(unconfUserRole)));
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findWithAuthorityByEmail(email);
//        doReturn(userRole).when(authorityService).findByAuthority(Authority.ROLE_USER);
//
//        userService.confirmByEmail(email);
//
//        List<AuthorityEntity> resultUserAuthority = userEntity.getAuthorities();
//
//        assertFalse(resultUserAuthority.contains(unconfUserRole));
//        assertTrue(resultUserAuthority.contains(userRole));
//
//        verify(userRepository, times(1)).findWithAuthorityByEmail(email);
//        verify(authorityService, times(1)).findByAuthority(Authority.ROLE_USER);
//        verify(userRepository, times(1)).save(userEntity);
//    }
//
//    @Test
//    @DisplayName("UT: confirmUserByEmail() should not add ROLE_USER if already present")
//    void confirmUserByEmail_whenRoleAlreadyPresent_shouldNotDuplicate() {
//        String email = "test@example.com";
//
//        AuthorityEntity roleUser = new AuthorityEntity();
//        roleUser.setAuthority(Authority.ROLE_USER);
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(email);
//        userEntity.setAuthorities(new ArrayList<>(List.of(roleUser)));
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findWithAuthorityByEmail(email);
//        doReturn(roleUser).when(authorityService).findByAuthority(Authority.ROLE_USER);
//
//        userService.confirmByEmail(email);
//
//        List<AuthorityEntity> updatedAuthorities = userEntity.getAuthorities();
//        assertEquals(1, updatedAuthorities.stream()
//                .filter(a -> a.getAuthority().equals(Authority.ROLE_USER)).count());
//
//        verify(userRepository, times(1)).findWithAuthorityByEmail(email);
//        verify(authorityService, times(1)).findByAuthority(Authority.ROLE_USER);
//        verify(userRepository, times(1)).save(userEntity);
//        verifyNoMoreInteractions(userRepository, authorityService);
//    }
//
//    @Test
//    @DisplayName("UT: confirmUserByEmail() should throw UserNotFoundByEmailException when user not found")
//    void confirmUserByEmail_whenNotFound_shouldThrowException() {
//        String email = "notfound@example.com";
//
//        doReturn(Optional.empty()).when(userRepository).findWithAuthorityByEmail(email);
//
//        assertThrows(UserNotFoundByEmailException.class,
//                () -> userService.confirmByEmail(email));
//
//        verify(userRepository, times(1)).findWithAuthorityByEmail(email);
//        verifyNoMoreInteractions(userRepository, authorityService);
//    }
//
//    @Test
//    @DisplayName("UT: systemFindById() should return SystemUserDto when user is found")
//    void systemFindById_shouldReturnSystemUserDto_whenUserFound() {
//        UUID id = UUID.randomUUID();
//        UserEntity userEntity = new UserEntity();
//        SystemUserDto expectedDto = new SystemUserDto(
//                id, "test@email.com",
//                "test_password",
//                List.of(Authority.ROLE_USER),
//                null, null);
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(id);
//        doReturn(expectedDto).when(userMapper).toSystemDto(userEntity);
//
//        SystemUserDto result = userService.systemFindById(id);
//
//        assertEquals(expectedDto, result);
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, times(1)).toSystemDto(userEntity);
//    }
//
//    @Test
//    @DisplayName("UT: systemFindById() should throw UserNotFoundByEmailException when user is not found")
//    void systemFindById_shouldThrowException_whenUserNotFound() {
//        UUID id = UUID.randomUUID();
//
//        doReturn(Optional.empty()).when(userRepository).findById(id);
//
//        assertThrows(UserNotFoundByEmailException.class, () -> userService.systemFindById(id));
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, never()).toSystemDto(any(UserEntity.class));
//    }
//
//    @Test
//    @DisplayName("UT: systemFindByEmail() should return SystemUserDto when user is found")
//    void systemFindByEmail_shouldReturnSystemUserDto_whenUserFound() {
//        String email = "test@example.com";
//        UserEntity userEntity = new UserEntity();
//        UUID id = UUID.randomUUID();
//        SystemUserDto expectedDto = new SystemUserDto(
//                id, "test@email.com",
//                "test_password",
//                List.of(Authority.ROLE_USER),
//                null, null);
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findByEmail(email);
//        doReturn(expectedDto).when(userMapper).toSystemDto(userEntity);
//
//        SystemUserDto result = userService.systemFindByEmail(email);
//
//        assertEquals(expectedDto, result);
//        verify(userRepository, times(1)).findByEmail(email);
//        verify(userMapper, times(1)).toSystemDto(userEntity);
//    }
//
//    @Test
//    @DisplayName("UT: systemFindByEmail() should throw UserNotFoundByEmailException when user is not found")
//    void systemFindByEmail_shouldThrowException_whenUserNotFound() {
//        String email = "test@example.com";
//
//        doReturn(Optional.empty()).when(userRepository).findByEmail(email);
//
//        assertThrows(UserNotFoundByEmailException.class, () -> userService.systemFindByEmail(email));
//        verify(userRepository, times(1)).findByEmail(email);
//        verify(userMapper, never()).toSystemDto(any(UserEntity.class));
//    }
//
//    @Test
//    @DisplayName("UT: findById() should return UserResponseDto when user is found")
//    void findById_shouldReturnUserResponseDto_whenUserFound() {
//        UUID id = UUID.randomUUID();
//        UserEntity userEntity = new UserEntity();
//        UserResponseDto expectedDto = new UserResponseDto(id, "test@email.com", Instant.now());
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(id);
//        doReturn(expectedDto).when(userMapper).toResponseDto(userEntity);
//
//        UserResponseDto result = userService.findById(id);
//
//        assertEquals(expectedDto, result);
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, times(1)).toResponseDto(userEntity);
//    }
//
//    @Test
//    @DisplayName("UT: findById() should throw UserNotFoundByIdException when user is not found")
//    void findById_shouldThrowException_whenUserNotFound() {
//        UUID id = UUID.randomUUID();
//
//        doReturn(Optional.empty()).when(userRepository).findById(id);
//
//        assertThrows(UserNotFoundByIdException.class, () -> userService.findById(id));
//        verify(userRepository, times(1)).findById(id);
//        verify(userMapper, never()).toResponseDto(any());
//    }
//
//    @Test
//    @DisplayName("UT: deleteById() should delegate to userRepository.deleteById()")
//    void deleteById_shouldDelegateToUserRepository() {
//        UUID id = UUID.randomUUID();
//
//        doNothing().when(userRepository).deleteById(id);
//
//        userService.deleteById(id);
//
//        verify(userRepository, times(1)).deleteById(id);
//        verifyNoMoreInteractions(userRepository);
//    }
//
//    @Test
//    @DisplayName("UT: deleteByPassword() should delete user when password matches")
//    void deleteByPassword_shouldDeleteUser_whenPasswordMatches() {
//        UUID userId = UUID.randomUUID();
//        String rawPassword = "password123";
//        String encodedPassword = "$2a$10$encodedFakePassword";
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userId);
//        userEntity.setPassword(encodedPassword);
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(userId);
//        doReturn(true).when(passwordEncoder).matches(rawPassword, encodedPassword);
//
//        userService.deleteByPassword(userId, rawPassword);
//
//        verify(userRepository, times(1)).findById(userId);
//        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
//        verify(userRepository, times(1)).deleteById(userId);
//        verifyNoMoreInteractions(userRepository, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("UT: deleteByPassword() should throw IncorrectPasswordException when password doesn't match")
//    void deleteByPassword_shouldThrowException_whenPasswordIncorrect() {
//        UUID userId = UUID.randomUUID();
//        String rawPassword = "wrongPassword";
//        String encodedPassword = "$2a$10$encodedFakePassword";
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userId);
//        userEntity.setPassword(encodedPassword);
//
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(userId);
//        doReturn(false).when(passwordEncoder).matches(rawPassword, encodedPassword);
//
//        assertThrows(IncorrectPasswordException.class, () -> userService.deleteByPassword(userId, rawPassword));
//
//        verify(userRepository, times(1)).findById(userId);
//        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
//        verifyNoMoreInteractions(userRepository, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("UT: deleteByPassword() should throw UserNotFoundByIdException when user not found")
//    void deleteByPassword_shouldThrowException_whenUserNotFound() {
//        UUID userId = UUID.randomUUID();
//        String rawPassword = "password123";
//
//        doReturn(Optional.empty()).when(userRepository).findById(userId);
//
//        assertThrows(UserNotFoundByIdException.class, () -> userService.deleteByPassword(userId, rawPassword));
//
//        verify(userRepository, times(1)).findById(userId);
//        verifyNoMoreInteractions(userRepository);
//        verifyNoInteractions(passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("UT: mapToUpdateDto() should delegate to userMapper.toSystemUpdateDto() and return result")
//    void mapToUpdateDto_shouldDelegateToMapper() {
//        UUID id = UUID.randomUUID();
//        UserUpdateDto userUpdateDto = new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword");
//
//        SystemUserUpdateDto systemUserUpdateDto = new SystemUserUpdateDto(
//                id,
//                userUpdateDto.email(),
//                userUpdateDto.password(),
//                List.of(Authority.ROLE_USER)
//        );
//        when(userMapper.toSystemUpdateDto(userUpdateDto)).thenReturn(systemUserUpdateDto);
//
//        SystemUserUpdateDto result = userService.mapToUpdateDto(userUpdateDto);
//
//        assertEquals(systemUserUpdateDto, result);
//        verify(userMapper, times(1)).toSystemUpdateDto(userUpdateDto);
//        verifyNoMoreInteractions(userMapper);
//    }
//}
