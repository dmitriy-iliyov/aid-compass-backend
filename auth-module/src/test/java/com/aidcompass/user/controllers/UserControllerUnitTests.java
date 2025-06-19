//package com.aidcompass.user.controllers;
//
//import com.aidcompass.clients.RecoveryRequestDto;
//import com.aidcompass.clients.confirmation.ConfirmationService;
//import com.aidcompass.user.models.dto.UserRegistrationDto;
//import com.aidcompass.user.models.dto.UserResponseDto;
//import com.aidcompass.user.models.dto.UserUpdateDto;
//import com.aidcompass.user.services.UserFacade;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.Instant;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerUnitTests {
//
//    @Mock
//    private UserFacade userFacade;
//
//    @Mock
//    private ConfirmationService confirmationService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Test
//    @DisplayName("UT Should return 201 CREATED and send confirmation when registering a valid user")
//    void createUser_whenValidRequest_shouldReturn201() {
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("test@gmail.com", "test_password");
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        ResponseEntity<?> response = userController.createUser(userRegistrationDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("A confirmation email has been sent to your email address." +
//                "Please check your inbox to complete registration.", response.getBody());
//        verify(userFacade, times(1)).save(any(UserRegistrationDto.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(1)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("UT Should return 201 CREATED and send confirmation when registering a valid user with strange email")
//    void createUser_whenStrangeEmail_shouldReturn201() {
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("test+user.name@ex-ample.co.uk", "test_password");
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        ResponseEntity<?> response = userController.createUser(userRegistrationDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("A confirmation email has been sent to your email address." +
//                "Please check your inbox to complete registration.", response.getBody());
//        verify(userFacade, times(1)).save(any(UserRegistrationDto.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(1)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("UT Should return 200 OK when fetching an existing user by ID")
//    void getUser_whenValidRequest_shouldReturn200() {
//        UUID id = UUID.randomUUID();
//        UserResponseDto userResponseDto = new UserResponseDto(id, "testu@example.com", Instant.now());
//        doReturn(userResponseDto).when(userFacade).findById(any(UUID.class));
//
//        ResponseEntity<UserResponseDto> response = userController.getUser(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(userResponseDto, response.getBody());
//        verify(userFacade, times(1)).findById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("UT Should return 200 OK when updating user successfully")
//    void updatedUserWithReturns_whenValidRequest_shouldReturn200() {
//        UUID id = UUID.randomUUID();
//        UserResponseDto dto = new UserResponseDto(id, "testu@example.com", Instant.now());
//        doReturn(dto).when(userFacade).update(any(UUID.class), any(UserUpdateDto.class));
//
//        ResponseEntity<?> response = userController.updatedUser(id, new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword"), true);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(dto, response.getBody());
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("UT Should return 200 OK when updating user successfully")
//    void updateUser_whenValidRequest_shouldReturn200() {
//        UUID id = UUID.randomUUID();
//        doReturn(null).when(userFacade).update(any(UUID.class), any(UserUpdateDto.class));
//
//        ResponseEntity<?> response = userController.updatedUser(id, new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword"), false);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("UT Should return 204 NO_CONTENT when deleting user by ID")
//    void deleteUser_whenValidRequest_shouldReturn204() {
//        UUID id = UUID.randomUUID();
//        doNothing().when(userFacade).deleteById(any(UUID.class));
//
//        ResponseEntity<?> response = userController.deleteUser(id);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        verify(userFacade, times(1)).deleteById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("UT Should return 204 NO_CONTENT when deleting user by password")
//    void deleteUserByPassword_whenValidRequest_shouldReturn204() {
//        UUID id = UUID.randomUUID();
//        doNothing().when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        ResponseEntity<?> response = userController.deleteUserByPassword(id, "");
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        verify(userFacade, times(1)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
////    @Test
////    @DisplayName("UT Should return 204 NO_CONTENT when confirming user by email")
////    void confirmEmail_whenValidRequest_shouldReturn204() {
////        doNothing().when(userFacade).confirmByEmail(any(String.class));
////
////        ResponseEntity<?> response = userController.confirmEmail("");
////        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
////
////        verify(userFacade, times(1)).confirmByEmail(any(String.class));
////        verifyNoMoreInteractions(userFacade);
////    }
////
////    @Test
////    @DisplayName("UT Should return 204 NO_CONTENT when recovering password via email")
////    void recoverPasswordByEmail_whenValidRequest_shouldReturn204() {
////        RecoveryRequestDto recoveryRequestDto = new RecoveryRequestDto("test@gmail.com", "test_password");
////        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
////
////        ResponseEntity<?> response = userController.recoveryPasswordByPassword(recoveryRequestDto);
////        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
////
////        verify(userFacade, times(1)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
////        verifyNoMoreInteractions(userFacade);
////    }
//}
