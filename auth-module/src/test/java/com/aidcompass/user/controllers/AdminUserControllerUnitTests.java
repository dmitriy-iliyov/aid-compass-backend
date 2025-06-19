//package com.aidcompass.user.controllers;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.Instant;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class AdminUserControllerUnitTests {
//
//    private UserFacade userFacade;
//    private AdminUserController adminUserController;
//
//    @BeforeEach
//    void setUp() {
//        userFacade = mock(UserFacade.class);
//        adminUserController = new AdminUserController(userFacade);
//    }
//
//    @Test
//    @DisplayName("UT getUserById should return UserResponseDto with 200 status")
//    void getUserById_shouldReturnUser() {
//        UUID userId = UUID.randomUUID();
//        UserResponseDto userResponseDto = new UserResponseDto(userId, "testu@example.com", Instant.now());
//        when(userFacade.findById(userId)).thenReturn(userResponseDto);
//
//        ResponseEntity<?> response = adminUserController.getUserById(userId.toString());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo(userResponseDto);
//        verify(userFacade).findById(userId);
//    }
//
//    @Test
//    @DisplayName("UT updatedUserById should update user and return 204")
//    void updatedUserById_shouldUpdateUser() {
//        UUID userId = UUID.randomUUID();
//        UserUpdateDto userUpdateDto = new UserUpdateDto("testu@example.com", "test_oihugyuiokiuhhijpassword");
//
//        ResponseEntity<?> response = adminUserController.updatedUserById(userId.toString(), userUpdateDto, false);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        verify(userFacade, times(1)).update(userId, userUpdateDto);
//    }
//
//    @Test
//    @DisplayName("UT deleteUserById should delete user and return 204")
//    void deleteUserById_shouldDeleteUser() {
//        UUID userId = UUID.randomUUID();
//
//        ResponseEntity<?> response = adminUserController.deleteUserById(userId.toString());
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        verify(userFacade).deleteById(userId);
//    }
//}
