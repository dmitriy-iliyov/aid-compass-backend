//package com.aidcompass.user.services;
//
//import com.aidcompass.authority.models.Authority;
//import com.aidcompass.clients.RecoveryRequestDto;
//import com.aidcompass.exceptions.not_found.EmailNotFoundException;
//import com.aidcompass.exceptions.not_found.UnconfirmedUserNotFoundByEmailException;
//import com.aidcompass.exceptions.not_found.UserNotFoundByEmailException;
//import com.aidcompass.global_exceptions.BaseNotFoundException;
//import com.aidcompass.global_exceptions.UserNotFoundException;
//import com.aidcompass.user.models.dto.*;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.Validator;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@ExtendWith(MockitoExtension.class)
//public class UserFacadeUnitTests {
//
//    @Mock
//    UserService userService;
//
//    @Mock
//    UnconfirmedUserService unconfirmedUserService;
//
//    @Mock
//    Validator validator;
//
//    @InjectMocks
//    UserFacadeImpl userFacadeImpl;
//
//
//    @Test
//    @DisplayName("UT: save() should delegate to UnconfirmedUserService.save() with provided UserRegistrationDto")
//    void save() {
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("test@gmail.com", "test_password");
//        doNothing().when(unconfirmedUserService).save(any(UserRegistrationDto.class));
//
//        userFacadeImpl.save(userRegistrationDto);
//
//        verify(unconfirmedUserService, times(1)).save(any(UserRegistrationDto.class));
//        verifyNoMoreInteractions(unconfirmedUserService);
//    }
//
//    @Test
//    @DisplayName("UT: confirmEmail() should update authority when user is found in system")
//    void confirmByEmail_shouldUpdateAuthority_whenUserExists() {
//        String email = "test@gmail.com";
//        doNothing().when(userService).confirmByEmail(email);
//
//        userFacadeImpl.confirmByEmail(email);
//
//        verify(userService, times(1)).confirmByEmail(email);
//        verifyNoInteractions(unconfirmedUserService);
//    }
//
//    @Test
//    @DisplayName("UT: confirmEmail() should move unconfirmedUser to userRepo when user not exist in userRepo")
//    void confirmByEmail_shouldMoveUnconfirmedUserToUserRepo_whenUserNotExists() {
//        String email = "test@gmail.com";
//        doThrow(new UserNotFoundByEmailException()).when(userService).confirmByEmail(email);
//
//        userFacadeImpl.confirmByEmail(email);
//
//        verify(userService, times(1)).confirmByEmail(email);
//        verify(unconfirmedUserService, times(1)).systemFindByEmail(email);
//        verify(userService, times(1)).save(any());
//        verify(unconfirmedUserService, times(1)).deleteByEmail(any());
//        verifyNoMoreInteractions(userService, unconfirmedUserService);
//    }
//
//    @Test
//    @DisplayName("UT: confirmEmail() should throw when unconfirmed user not found after NotFoundException")
//    void confirmByEmail_shouldThrow_whenUnconfirmedUserNotFound() {
//        String email = "missing@example.com";
//
//        doThrow(UserNotFoundException.class).when(userService).confirmByEmail(email);
//        doThrow(UnconfirmedUserNotFoundByEmailException.class).when(unconfirmedUserService).systemFindByEmail(email);
//
//        assertThrows(UnconfirmedUserNotFoundByEmailException.class, () -> userFacadeImpl.confirmByEmail(email));
//
//        verify(userService, times(1)).confirmByEmail(email);
//        verify(unconfirmedUserService, times(1)).systemFindByEmail(email);
//        verify(userService, times(0)).save(any());
//        verify(unconfirmedUserService, times(0)).deleteByEmail(any());
//    }
//
//    @Test
//    @DisplayName("UT: confirmEmail() should not delete unconfirmed user if save throws exception")
//    void confirmByEmail_shouldNotDelete_ifSaveFails() {
//        String email = "test@example.com";
//        SystemUserDto systemUserDto = new SystemUserDto(UUID.randomUUID(), email, "pass", List.of(), Instant.now(), Instant.now());
//
//        doThrow(UserNotFoundException.class).when(userService).confirmByEmail(email);
//        doReturn(systemUserDto).when(unconfirmedUserService).systemFindByEmail(email);
//        doThrow(RuntimeException.class).when(userService).save(systemUserDto);
//
//        assertThrows(RuntimeException.class, () -> userFacadeImpl.confirmByEmail(email));
//
//        verify(userService, times(1)).confirmByEmail(email);
//        verify(unconfirmedUserService, times(1)).systemFindByEmail(email);
//        verify(userService, times(1)).save(systemUserDto);
//        verify(unconfirmedUserService, times(0)).deleteByEmail(email);
//    }
//
//    @Test
//    @DisplayName("UT: confirmEmail() should work with emails containing special characters")
//    void confirmEmail_shouldHandleSpecialCharactersInByEmail() {
//        String email = "test+user.name@ex-ample.co.uk";
//
//        doNothing().when(userService).confirmByEmail(email);
//
//        userFacadeImpl.confirmByEmail(email);
//
//        verify(userService, times(1)).confirmByEmail(email);
//        verifyNoInteractions(unconfirmedUserService);
//    }
//
//    @Test
//    @DisplayName("UT: existsByEmail() should return true if userService.existsByEmail returns true")
//    void existsByEmail_shouldReturnTrue_ifUserServiceReturnsTrue() {
//        String email = "user@example.com";
//        doReturn(true).when(userService).existsByEmail(email);
//
//        boolean result = userFacadeImpl.existsByEmail(email);
//
//        assertTrue(result);
//        verify(userService, times(1)).existsByEmail(email);
//        verify(unconfirmedUserService, times(0)).existsByEmail(email);
//    }
//
//    @Test
//    @DisplayName("UT: existsByEmail() should return true if unconfirmedUserService.existsByEmail returns true")
//    void existsByEmail_shouldReturnTrue_ifUnconfirmedUserServiceReturnsTrue() {
//        String email = "user@example.com";
//        doReturn(false).when(userService).existsByEmail(email);
//        doReturn(true).when(unconfirmedUserService).existsByEmail(email);
//
//        boolean result = userFacadeImpl.existsByEmail(email);
//
//        assertTrue(result);
//        verify(userService, times(1)).existsByEmail(email);
//        verify(unconfirmedUserService, times(1)).existsByEmail(email);
//    }
//
//    @Test
//    @DisplayName("UT: existsByEmail() should return false if neither service returns true")
//    void existsByEmail_shouldReturnFalse_ifBothReturnFalse() {
//        String email = "user@example.com";
//        doReturn(false).when(userService).existsByEmail(email);
//        doReturn(false).when(unconfirmedUserService).existsByEmail(email);
//
//        boolean result = userFacadeImpl.existsByEmail(email);
//
//        assertFalse(result);
//        verify(userService, times(1)).existsByEmail(email);
//        verify(unconfirmedUserService, times(1)).existsByEmail(email);
//    }
//
//    @Test
//    @DisplayName("UT: systemFindById() should return result from userService")
//    void systemFindById_shouldDelegateToUserService() {
//        UUID id = UUID.randomUUID();
//        String email = "test@example.com";
//        SystemUserDto expected = new SystemUserDto(UUID.randomUUID(), email, "pass", List.of(), Instant.now(), Instant.now());
//        doReturn(expected).when(userService).systemFindById(id);
//
//        SystemUserDto result = userFacadeImpl.systemFindById(id);
//
//        assertEquals(expected, result);
//        verify(userService, times(1)).systemFindById(id);
//    }
//
//    @Test
//    @DisplayName("UT: systemFindByEmail() should return result from userService if found")
//    void systemFindByEmail_shouldReturnFromUserService() throws BaseNotFoundException {
//        String email = "test@example.com";
//        SystemUserDto expected = new SystemUserDto(UUID.randomUUID(), email, "pass", List.of(), Instant.now(), Instant.now());
//        doReturn(expected).when(userService).systemFindByEmail(email);
//
//        SystemUserDto result = userFacadeImpl.systemFindByEmail(email);
//
//        assertEquals(expected, result);
//        verify(userService, times(1)).systemFindByEmail(email);
//        verify(unconfirmedUserService, times(0)).systemFindByEmail(any());
//    }
//
//    @Test
//    @DisplayName("UT: systemFindByEmail() should return from unconfirmedUserService if userService throws NotFoundException")
//    void systemFindByEmail_shouldFallbackToUnconfirmed_ifNotFoundInUserService() throws BaseNotFoundException {
//        String email = "notfound@example.com";
//        SystemUserDto fallback = new SystemUserDto(UUID.randomUUID(), email, "pass", List.of(), Instant.now(), Instant.now());
//
//        doThrow(UserNotFoundException.class).when(userService).systemFindByEmail(email);
//        doReturn(fallback).when(unconfirmedUserService).systemFindByEmail(email);
//
//        SystemUserDto result = userFacadeImpl.systemFindByEmail(email);
//
//        assertEquals(fallback, result);
//        verify(userService, times(1)).systemFindByEmail(email);
//        verify(unconfirmedUserService, times(1)).systemFindByEmail(email);
//    }
//
//    @Test
//    @DisplayName("UT: findById() should return user response from userService")
//    void findById_shouldDelegateToUserService() {
//        UUID id = UUID.randomUUID();
//        UserResponseDto expected = new UserResponseDto(id, "email@example.com", Instant.now());
//        doReturn(expected).when(userService).findById(id);
//
//        UserResponseDto result = userFacadeImpl.findById(id);
//
//        assertEquals(expected, result);
//        verify(userService, times(1)).findById(id);
//    }
//
//    @Test
//    @DisplayName("UT: update() should map, validate and update user when validation passes")
//    void update() {
//        UUID id = UUID.randomUUID();
//        UserUpdateDto userUpdateDto = new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword");
//        UserResponseDto dto = new UserResponseDto(id, userUpdateDto.email(), Instant.now());
//
//        doReturn(new SystemUserUpdateDto(id, userUpdateDto.email(), userUpdateDto.password(), List.of(Authority.ROLE_USER)))
//                .when(userService).mapToUpdateDto(any(UserUpdateDto.class));
//        doReturn(new HashSet<>()).when(validator).validate(any(SystemUserUpdateDto.class));
//        doReturn(dto).when(userService).update(any(SystemUserUpdateDto.class));
//
//        UserResponseDto userResponseDto = userFacadeImpl.update(id, userUpdateDto);
//
//        assertEquals(userResponseDto, dto);
//        verify(userService, times(1)).mapToUpdateDto(any(UserUpdateDto.class));
//        verify(userService, times(1)).update(any(SystemUserUpdateDto.class));
//        verify(validator, times(1)).validate(any(SystemUserUpdateDto.class));
//        verifyNoMoreInteractions(userService, validator);
//    }
//
//    @Test
//    @DisplayName("UT: update() should throw ConstraintViolationException when validation fails")
//    void update_shouldThrowException() {
//        UUID id = UUID.randomUUID();
//        UserUpdateDto userUpdateDto = new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword");
//
//        SystemUserUpdateDto systemUserUpdateDto = new SystemUserUpdateDto(
//                id,
//                userUpdateDto.email(),
//                userUpdateDto.password(),
//                List.of(Authority.ROLE_USER)
//        );
//
//        doReturn(systemUserUpdateDto)
//                .when(userService).mapToUpdateDto(any(UserUpdateDto.class));
//
//        ConstraintViolation<SystemUserUpdateDto> violation = mock(ConstraintViolation.class);
//        Set<ConstraintViolation<SystemUserUpdateDto>> violations = Set.of(violation);
//
//        doReturn(violations)
//                .when(validator).validate(any(SystemUserUpdateDto.class));
//
//        assertThrowsExactly(
//                ConstraintViolationException.class,
//                () -> userFacadeImpl.update(id, userUpdateDto)
//        );
//
//        verify(userService, times(1)).mapToUpdateDto(any(UserUpdateDto.class));
//        verify(validator, times(1)).validate(systemUserUpdateDto);
//        verify(userService, never()).update(any(SystemUserUpdateDto.class));
//    }
//
//    @Test
//    @DisplayName("UT: recoverPasswordByEmail() should update password if email exists")
//    void recoverPasswordByEmail_shouldUpdatePassword_ifEmailExists() {
//        String email = "user@example.com";
//        String newPassword = "newSecret123";
//        RecoveryRequestDto dto = new RecoveryRequestDto(email, newPassword);
//
//        when(userService.existsByEmail(email)).thenReturn(true);
//
//        userFacadeImpl.recoverPasswordByEmail(dto);
//
//        verify(userService, times(1)).existsByEmail(email);
//        verify(userService, times(1)).updatePasswordByEmail(email, newPassword);
//    }
//
//    @Test
//    @DisplayName("UT: recoverPasswordByEmail() should throw exception if email does not exist")
//    void recoverPasswordByEmail_shouldThrow_ifEmailDoesNotExist() {
//        String email = "unknown@example.com";
//        RecoveryRequestDto dto = new RecoveryRequestDto(email, "password");
//
//        when(userService.existsByEmail(email)).thenReturn(false);
//
//        assertThrows(EmailNotFoundException.class, () -> userFacadeImpl.recoverPasswordByEmail(dto));
//
//        verify(userService, times(1)).existsByEmail(email);
//        verify(userService, never()).updatePasswordByEmail(any(), any());
//    }
//
//    @Test
//    @DisplayName("UT: deleteById() should call userService.deleteById() once")
//    void deleteById_shouldDelegateToUserService() {
//        UUID userId = UUID.randomUUID();
//
//        userFacadeImpl.deleteById(userId);
//
//        verify(userService, times(1)).deleteById(userId);
//    }
//
//    @Test
//    @DisplayName("UT: deleteByPassword() should call userService.deleteByPassword() once")
//    void deleteByPassword_shouldDelegateToUserService() {
//        UUID userId = UUID.randomUUID();
//        String password = "securePassword";
//
//        userFacadeImpl.deleteByPassword(userId, password);
//
//        verify(userService, times(1)).deleteByPassword(userId, password);
//    }
//
//}
