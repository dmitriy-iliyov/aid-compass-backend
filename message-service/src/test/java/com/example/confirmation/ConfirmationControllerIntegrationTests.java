package com.example.confirmation;

import com.example.exceptions.MessageModuleControllerAdvice;
import com.example.exceptions.models.BaseInvalidConfirmationTokenException;
import com.example.mapper.ExceptionMapperImpl;
import io.lettuce.core.RedisConnectionException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConfirmationController.class)
@Import({MessageModuleControllerAdvice.class, ExceptionMapperImpl.class})
@ContextConfiguration(classes = {ConfirmationController.class})
public class ConfirmationControllerIntegrationTests {

    @MockitoBean
    ConfirmationService confirmationService;

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("IT: confirmEmail() should redirect to /users/login on valid token")
    void confirmEmail_whenTokenValid_shouldReturnSeeOther() throws Exception {
        String token = "token-example";

        doNothing().when(confirmationService).validateConfirmationToken(token);

        mockMvc.perform(post("/api/confirm/email")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/api/users/login"));

        verify(confirmationService, times(1)).validateConfirmationToken(token);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: confirmEmail() should return BAD_REQUEST")
    void confirmEmail_whenTokenValid_shouldReturnNotFound() throws Exception {
        String token = "token-example";

        doThrow(new BaseInvalidConfirmationTokenException()).when(confirmationService).validateConfirmationToken(token);

        mockMvc.perform(post("/api/confirm/email")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.properties.errors[0].message")
                        .value("Confirmation token is invalid!"));

        verify(confirmationService, times(1)).validateConfirmationToken(token);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: getToken() should return CREATED")
    void getToken_shouldReturnCreated() throws Exception {
        String resource = "resource-example";

        doNothing().when(confirmationService).sendConfirmationMessage(resource);

        mockMvc.perform(post("/api/confirm/request")
                        .param("resource", resource)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(confirmationService, times(1)).sendConfirmationMessage(resource);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: getToken() when redis disconnected should return 500")
    void getToken_whenRedisDisconnected_shouldReturn500() throws Exception {
        String resource = "resource-example";

        doThrow(new RedisConnectionException("test redis disconnected message")).when(confirmationService)
                .sendConfirmationMessage(resource);

        mockMvc.perform(post("/api/confirm/request")
                        .param("resource", resource)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(confirmationService, times(1)).sendConfirmationMessage(resource);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: confirmEmail() when redis disconnected should return 500")
    void confirmEmail_whenRedisDisconnected_shouldReturn500() throws Exception {
        String token = "token-example";

        doThrow(new RedisConnectionException("test message")).when(confirmationService).validateConfirmationToken(token);

        mockMvc.perform(post("/api/confirm/email")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(confirmationService, times(1)).validateConfirmationToken(token);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: getToken() when trouble with company mail should return 500")
    void getToken_whenMailException_shouldReturn500() throws Exception {
        String resource = "resource-example";

        doThrow(new MailAuthenticationException("test mail exception message")).when(confirmationService)
                .sendConfirmationMessage(resource);

        mockMvc.perform(post("/api/confirm/request")
                        .param("resource", resource)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.properties.errors[0].message")
                        .value("Sorry, problems with our email!"));

        verify(confirmationService, times(1)).sendConfirmationMessage(resource);
        verifyNoMoreInteractions(confirmationService);
    }

    @Test
    @DisplayName("IT: getToken() when trouble with company mail should return 500")
    void getToken_whenMessageException_shouldReturn500() throws Exception {
        String resource = "resource-example";

        doThrow(new MessagingException("test message exception")).when(confirmationService)
                .sendConfirmationMessage(resource);

        mockMvc.perform(post("/api/confirm/request")
                        .param("resource", resource)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.properties.errors[0].message")
                        .value("Error when sending email, please try again!"));

        verify(confirmationService, times(1)).sendConfirmationMessage(resource);
        verifyNoMoreInteractions(confirmationService);
    }
}
