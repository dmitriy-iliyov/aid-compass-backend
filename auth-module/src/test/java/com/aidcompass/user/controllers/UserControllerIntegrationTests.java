//package com.aidcompass.user.controllers;
//
//import com.aidcompass.clients.RecoveryRequestDto;
//import com.aidcompass.clients.confirmation.ConfirmationService;
//import com.aidcompass.exceptions.AuthModuleControllerAdvice;
//import com.aidcompass.exceptions.illegal_input.IncorrectPasswordException;
//import com.aidcompass.exceptions.not_found.EmailNotFoundException;
//import com.aidcompass.exceptions.not_found.UserNotFoundByIdException;
//import com.aidcompass.user.models.dto.UserRegistrationDto;
//import com.aidcompass.user.models.dto.UserResponseDto;
//import com.aidcompass.user.models.dto.UserUpdateDto;
//import com.aidcompass.mapper.ExceptionMapperImpl;
//import com.aidcompass.user.services.UserFacade;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.lettuce.core.RedisConnectionException;
//import jakarta.validation.ConstraintViolationException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.UUID;
//
//import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//@Import({AuthModuleControllerAdvice.class, ExceptionMapperImpl.class})
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@ContextConfiguration(classes = {UserController.class})
//public class UserControllerIntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private UserFacade userFacade;
//
//    @MockitoBean
//    private ConfirmationService confirmationService;
//
//    private final String ROOT_USER_URI ="/api/users";
//    private final String CREATE_USER_URI = ROOT_USER_URI;
//    private final String GET_USER_URI = ROOT_USER_URI + "/user/%s";
//    private final String UPDATE_USER_URI = ROOT_USER_URI + "/user/%s";
//    private final String DELETE_USER_URI = ROOT_USER_URI + "/user/%s";
//    private final String DELETE_WITH_PASS_USER_URI = DELETE_USER_URI + "/%s";
//    private final String CONFIRM_BY_EMAIL_URI = ROOT_USER_URI + "/confirm/email?email=%s";
//    private final String RECOVERY_PASS_BY_EMAIL_URI = ROOT_USER_URI + "/recover-password/email";
//
//
//    @Test
//    @DisplayName("IT Should catch RedisConnectionException")
//    void testRedisException() throws Exception {
//        String json = """
//                        {
//                          "email": "test@gmail.com",
//                          "password": "test_password"
//                        }
//                        """;
//        doThrow(new RedisConnectionException("test message")).when(userFacade).save(any(UserRegistrationDto.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isInternalServerError());
//
//        verify(userFacade, times(1)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 201 when user creation request is valid")
//    void createUser_whenValidRequest_shouldReturn201() throws Exception {
//        String json = """
//                        {
//                          "email": "test@gmail.com",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        ResultActions response = mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated());
//
//        json = response.andReturn().getResponse().getContentAsString();
//        assertEquals("A confirmation email has been sent to your email address." +
//                "Please check your inbox to complete registration.", json);
//
//        verify(userFacade, times(1)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(1)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when user with this email already exists")
//    void createUser_whenUserWithEmailIsExist_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "test@gmail.com",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("email"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email is in use!"));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email format is invalid")
//    void createUser_whenInvalidEmail_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "test_example.com",
//                          "password": "test_password"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("email"))
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email should be valid!"));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is too long")
//    void createUser_whenEmailSoLong_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "testuidfhbejwkfrgjkokwpeijugjfkodplokjegfkdlkljfgdlksfoe@example.com",
//                          "password": "test_password"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("email"))
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Email length must be greater than 11 and less than 50!"));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when create users email is {slashT}")
//    void createUser_whenEmailSlashT_shouldReturn400() throws Exception {
//
//        String json = """
//                        {
//                          "email": "\t",
//                          "password": "test_password"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(0)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when create users email is {slashN}")
//    void createUser_whenEmailSlashN_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "\n",
//                          "password": "test_password"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(0)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when create users password is {slashT}")
//    void createUser_whenPassSlashT_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "test@gmail.com",
//                          "password": "\t"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(0)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when create users password is {slashN}")
//    void createUser_whenPassSlashN_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "test@gmail.com",
//                          "password": "\n"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(0)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email and password are blank")
//    void createUser_whenRequestIsBlank_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": " ",
//                          "password": " "
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(5)))
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email length must be greater than 11 and less than 50!",
//                                "Password length must be greater than 10 and less than 22!",
//                                "Email should be valid!",
//                                "Password can't be empty or blank!",
//                                "Email shouldn't be empty or blank!"
//                        )
//                ))
//                .andExpect(jsonPath("$.properties.errors[*].field",
//                        hasItems("email", "password")
//                ));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email and password are empty")
//    void createUser_whenRequestIsEmpty_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "",
//                          "password": ""
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(4)))
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email length must be greater than 11 and less than 50!",
//                                "Password length must be greater than 10 and less than 22!",
//                                "Password can't be empty or blank!",
//                                "Email shouldn't be empty or blank!"
//                        )
//                ))
//                .andExpect(jsonPath("$.properties.errors[*].field",
//                        hasItems("email", "password")
//                ));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is too short")
//    void createUser_whenPassSoShort_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("password"))
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is too long")
//    void createUser_whenPassSoLong_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test_oihugyuiokiuhhijpassword"
//                        }
//                        """;
//        doNothing().when(userFacade).save(any(UserRegistrationDto.class));
//        doReturn(false).when(userFacade).existsByEmail(any(String.class));
//        doNothing().when(confirmationService).sendConfirmationMessage(any(String.class));
//
//        mockMvc.perform(post(CREATE_USER_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors", hasSize(1)))
//                .andExpect(jsonPath("$.properties.errors[0].field").value("password"))
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).save(any(UserRegistrationDto.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//        verify(confirmationService, times(0)).sendConfirmationMessage(any(String.class));
//        verifyNoMoreInteractions(confirmationService);
//    }
//
//
//    @Test
//    @DisplayName("IT Should return 200 when user is found")
//    void getUser_shouldReturn200() throws Exception {
//        UUID id = UUID.randomUUID();
//        UserResponseDto userResponseDto = new UserResponseDto(id, "testu@example.com", Instant.now());
//        doReturn(userResponseDto).when(userFacade).findById(any(UUID.class));
//
//        mockMvc.perform(get(GET_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id.toString()))
//                .andExpect(jsonPath("$.email").value(userResponseDto.email()))
//                .andExpect(jsonPath("$.createdAt").value(userResponseDto.createdAt().toString()))
//                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
//
//        verify(userFacade, times(1)).findById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 404 when user is not found")
//    void getUser_whenNotExist_shouldReturn404() throws Exception {
//        UUID id = UUID.randomUUID();
//        doThrow(new UserNotFoundByIdException()).when(userFacade).findById(any(UUID.class));
//
//        mockMvc.perform(get(GET_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("User isn't exist!"));
//
//        verify(userFacade, times(1)).findById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when user is successfully updated")
//    void updateUser_shouldReturn204() throws Exception {
//        UUID id = UUID.randomUUID();
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test_oihugyuiokiuhhijpassword"
//                        }
//                        """;
//
//        mockMvc.perform(put(UPDATE_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isNoContent());
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 404 when updating a non-existing user")
//    void updateUser_whenNotExist_shouldReturn404() throws Exception {
//        UUID id = UUID.randomUUID();
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test_oihugyuiokiuhhijpassword"
//                        }
//                        """;
//        doThrow(new UserNotFoundByIdException()).when(userFacade).update(any(UUID.class), any(UserUpdateDto.class));
//
//        mockMvc.perform(put(UPDATE_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("User isn't exist!"));
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when user update request is invalid")
//    void updateUser_whenBadRequest_shouldReturn400() throws Exception {
//        UUID id = UUID.randomUUID();
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test_oihugyuiokiuhhijpassword"
//                        }
//                        """;
//        doThrow(new ConstraintViolationException(new HashSet<>())).when(userFacade).update(any(UUID.class), any(UserUpdateDto.class));
//
//        mockMvc.perform(put(UPDATE_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when user is successfully updated")
//    void updatedUserWithReturns_shouldReturn204() throws Exception {
//        UUID id = UUID.randomUUID();
//        String json = """
//                        {
//                          "email": "testu@example.com",
//                          "password": "test_oihugyuiokiuhhijpassword"
//                        }
//                        """;
//
//        mockMvc.perform(put(UPDATE_USER_URI.formatted(id))
//                        .param("return_body", "true")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk());
//
//        verify(userFacade, times(1)).update(any(UUID.class), any(UserUpdateDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when user is successfully deleted")
//    void deleteUser_whenValidRequest_shouldReturn204() throws Exception {
//        UUID id = UUID.randomUUID();
//
//        doNothing().when(userFacade).deleteById(any(UUID.class));
//
//        mockMvc.perform(delete(DELETE_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(userFacade, times(1)).deleteById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when user update request is invalid")
//    void deleteUser_whenUserNotExist_shouldReturn404() throws Exception {
//        UUID id = UUID.randomUUID();
//
//        doThrow(new UserNotFoundByIdException()).when(userFacade).deleteById(any(UUID.class));
//
//        mockMvc.perform(delete(DELETE_USER_URI.formatted(id))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("User isn't exist!"));
//
//        verify(userFacade, times(1)).deleteById(any(UUID.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when user is successfully deleted")
//    void deleteUserByPassword_whenValidRequest_shouldReturn204() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "test_password";
//        doNothing().when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(userFacade, times(1)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 404 when deleting a non-existing user")
//    void deleteUserByPassword_whenUserNotExist_shouldReturn404() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "test_password";
//
//        doThrow(new UserNotFoundByIdException()).when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("User isn't exist!"));
//
//        verify(userFacade, times(1)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is too short for delete by password")
//    void deleteUserByPassword_whenPassSoShort_shouldReturn400() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "password";
//
//        doNothing().when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is too long for delete by password")
//    void deleteUserByPassword_whenPassSoLong_shouldReturn400() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "[wplekfowjrgihbqwkoefpjirgwepokfjirgheojpwirehfoepjrieeopjreorjijeri]";
//
//        doNothing().when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is incorrect for delete by password")
//    void deleteUserByPassword_whenPassIncorrect_shouldReturn400() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "password10";
//
//        doThrow(new IncorrectPasswordException()).when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Incorrect password!"));
//
//        verify(userFacade, times(1)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is blank for delete by password")
//    void deleteUserByPassword_whenPassBlank_shouldReturn400() throws Exception {
//        UUID id = UUID.randomUUID();
//        String password = "  ";
//
//        doNothing().when(userFacade).deleteByPassword(any(UUID.class), any(String.class));
//
//        mockMvc.perform(delete(DELETE_WITH_PASS_USER_URI.formatted(id, password))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Password length must be greater than 10 and less than 22!",
//                                "Password can't be empty or blank!"
//                        )
//                ));
//
//        verify(userFacade, times(0)).deleteByPassword(any(UUID.class), any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when email is successfully confirmed")
//    void confirmEmail_whenRequestValid_shouldReturn204() throws Exception {
//        String email = "test@gmail.com";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(userFacade, times(1)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 404 when email isn't exist")
//    void confirmEmail_whenEmailNotExist_shouldReturn404() throws Exception {
//        String email = "test@gmail.com";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doThrow(new EmailNotFoundException()).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Email isn't exist!"));
//
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is blank")
//    void confirmEmail_whenEmailBlank_shouldReturn400() throws Exception {
//        String email = "  ";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email should be valid!",
//                                "Email length must be greater than 11 and less than 50!",
//                                "Email shouldn't be empty or blank!"
//                        )
//                ))
//                .andExpect(jsonPath("$.properties.errors[*].field",
//                        hasItems("email")
//                ));
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is {slashT}")
//    void confirmEmail_whenEmailSlashT_shouldReturn400() throws Exception {
//        String email = "\t";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email shouldn't be empty or blank!"
//                        )
//                ));
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is {slashN}")
//    void confirmEmail_whenEmailSlashN_shouldReturn400() throws Exception {
//        String email = "\n";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email shouldn't be empty or blank!"
//                        )
//                ));
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email isn't valid")
//    void confirmEmail_whenEmailNotValid_shouldReturn400() throws Exception {
//        String email = "testinvalidemail";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message").value("Email should be valid!"));
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is so long valid")
//    void confirmEmail_whenEmailSoLong_shouldReturn400() throws Exception {
//        String email = "testinvalidemailoeirguhpojhghhjkljhiugyftdrfgyhjkljhugyfrjigoiopr3ituh@gmail.com";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message",
//                        hasItems(
//                                "Email should be valid!",
//                                "Email length must be greater than 11 and less than 50!"
//                        )
//                ))
//                .andExpect(jsonPath("$.properties.errors[*].field",
//                        hasItems("email")
//                ));
//
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when email is so short")
//    void confirmEmail_whenEmailSoShort_shouldReturn400() throws Exception {
//        String email = "te@g.com";
//
//        doNothing().when(userFacade).confirmByEmail(any(String.class));
//        doReturn(true).when(userFacade).existsByEmail(any(String.class));
//
//        mockMvc.perform(patch(CONFIRM_BY_EMAIL_URI.formatted(email))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Email length must be greater than 11 and less than 50!"));
//
//        verify(userFacade, times(0)).confirmByEmail(any(String.class));
//        verify(userFacade, times(1)).existsByEmail(any(String.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 204 when pass is successfully recovered")
//    void recoveryPass_whenRequestValid_shouldReturn204() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isNoContent());
//
//        verify(userFacade, times(1)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when resource is blank")
//    void recoveryPass_whenResourceBlank_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "  ",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItems("Resource can't be empty or blank!")));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when pass is blank")
//    void recoveryPass_whenPassBlank_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "  "
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[*].message", hasItems(("Password can't be empty or blank!"))));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when resource is {slashN}")
//    void recoveryPass_whenResourceSlashN_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "\n",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when pass is {slashN}")
//    void recoveryPass_whenPassSlashN_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "\n"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when resource is {slashT}")
//    void recoveryPass_whenResourceSlashT_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "\t",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when pass is {slashT}")
//    void recoveryPass_whenPassSlashT_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "\t"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest());
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when resource is so short")
//    void recoveryPass_whenResourceSoShort_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "t@g.com",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Resource length must be greater than 11 and less than 50!"));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when resource is so long")
//    void recoveryPass_whenResourceSoLong_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "testkoijoofiroejfeojfoeijfoeirjfoerjfoerijfoerijforijfro@gmail.com",
//                          "password": "test_password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message").value("Resource length must be greater than 11 and less than 50!"));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is so short")
//    void recoveryPass_whenPassSoShort_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "password"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//
//    @Test
//    @DisplayName("IT Should return 400 when password is so long")
//    void recoveryPass_whenPassSoLong_shouldReturn400() throws Exception {
//        String json = """
//                        {
//                          "resource": "test@gmail.com",
//                          "password": "testkoijoofiroejfeojfoeijfoeirjfoerjfoerijfoerijforijfro@gmail.com"
//                        }
//                        """;
//
//        doNothing().when(userFacade).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//
//        mockMvc.perform(patch(RECOVERY_PASS_BY_EMAIL_URI)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Password length must be greater than 10 and less than 22!"));
//
//        verify(userFacade, times(0)).recoverPasswordByEmail(any(RecoveryRequestDto.class));
//        verifyNoMoreInteractions(userFacade);
//    }
//}
