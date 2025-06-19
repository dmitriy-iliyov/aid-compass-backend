//package com.aidcompass.user.services;
//
//
//import com.aidcompass.dto.SystemUserDto;
//import com.aidcompass.dto.UserRegistrationDto;
//import com.aidcompass.enums.Authority;
//import com.aidcompass.exceptions.not_found.UnconfirmedUserNotFoundByEmailException;
//import com.aidcompass.user.repositories.UnconfirmedUserRepository;
//import com.aidcompass.user.mapper.UserMapper;
//import com.aidcompass.user.models.UnconfirmedUserEntity;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UnconfirmedUserServiceUnitTests {
//
//    @Mock
//    UnconfirmedUserRepository unconfirmedUserRepository;
//
//    @Mock
//    UserMapper userMapper;
//
//    @InjectMocks
//    UnconfirmedUserServiceImpl unconfirmedUserService;
//
//
//    @DisplayName("UT: save() should convert dto to entity and save")
//    @Test
//    void save_shouldConvertAndSaveEntity() {
//        UserRegistrationDto dto = new UserRegistrationDto("test@email.com", "password");
//        UnconfirmedUserEntity entity = new UnconfirmedUserEntity();
//
//        doReturn(entity).when(userMapper).toUnconfirmedEntity(dto);
//
//        unconfirmedUserService.save(dto);
//
//        verify(userMapper, times(1)).toUnconfirmedEntity(dto);
//        verify(unconfirmedUserRepository, times(1)).save(entity);
//        verifyNoMoreInteractions(userMapper, unconfirmedUserRepository);
//    }
//
//    @DisplayName("UT: existsByEmail() should return true if user exists")
//    @Test
//    void existsByEmail_shouldReturnTrueIfExists() {
//        String email = "test@email.com";
//        doReturn(true).when(unconfirmedUserRepository).existsById(email);
//
//        boolean result = unconfirmedUserService.existsByEmail(email);
//
//        assertTrue(result);
//        verify(unconfirmedUserRepository, times(1)).existsById(email);
//        verifyNoMoreInteractions(unconfirmedUserRepository);
//    }
//
//    @DisplayName("UT: existsByEmail() should return false if user does not exist")
//    @Test
//    void existsByEmail_shouldReturnFalseIfNotExists() {
//        String email = "notfound@email.com";
//        doReturn(false).when(unconfirmedUserRepository).existsById(email);
//
//        boolean result = unconfirmedUserService.existsByEmail(email);
//
//        assertFalse(result);
//        verify(unconfirmedUserRepository, times(1)).existsById(email);
//        verifyNoMoreInteractions(unconfirmedUserRepository);
//    }
//
//    @DisplayName("UT: systemFindByEmail() should return SystemUserDto when user exists")
//    @Test
//    void systemFindByEmail_shouldReturnDtoIfFound() {
//        String email = "test@email.com";
//        UnconfirmedUserEntity entity = new UnconfirmedUserEntity();
//        UUID id = UUID.randomUUID();
//        SystemUserDto dto = new SystemUserDto(
//                id, "test@email.com",
//                "test_password",
//                List.of(Authority.ROLE_USER),
//                null, null);
//
//        doReturn(Optional.of(entity)).when(unconfirmedUserRepository).findById(email);
//        doReturn(dto).when(userMapper).toSystemDto(entity);
//
//        SystemUserDto result = unconfirmedUserService.systemFindByEmail(email);
//
//        assertEquals(dto, result);
//        verify(unconfirmedUserRepository, times(1)).findById(email);
//        verify(userMapper, times(1)).toSystemDto(entity);
//        verifyNoMoreInteractions(unconfirmedUserRepository, userMapper);
//    }
//
//    @DisplayName("UT: systemFindByEmail() should throw if user not found")
//    @Test
//    void systemFindByEmail_shouldThrowIfNotFound() {
//        String email = "missing@email.com";
//        doReturn(Optional.empty()).when(unconfirmedUserRepository).findById(email);
//
//        assertThrows(UnconfirmedUserNotFoundByEmailException.class,
//                () -> unconfirmedUserService.systemFindByEmail(email));
//
//        verify(unconfirmedUserRepository, times(1)).findById(email);
//        verifyNoMoreInteractions(unconfirmedUserRepository);
//        verifyNoInteractions(userMapper);
//    }
//
//    @DisplayName("UT: deleteByEmail() should call deleteById with email")
//    @Test
//    void deleteByEmail_shouldCallRepository() {
//        String email = "test@email.com";
//
//        unconfirmedUserService.deleteByEmail(email);
//
//        verify(unconfirmedUserRepository, times(1)).deleteById(email);
//        verifyNoMoreInteractions(unconfirmedUserRepository);
//    }
//}
