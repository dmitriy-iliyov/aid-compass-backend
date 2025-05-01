package com.aidcompass.message.confirmation;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ConfirmationControllerUnitTests {

    @Mock
    ConfirmationService confirmationService;

    @InjectMocks
    ConfirmationController confirmationController;

    @DisplayName("UT: confirmEmail() should validate token and return 303 redirect to /users/login")
    @Test
    void confirmEmail_shouldValidateTokenAndRedirect() {
        String token = "test-token";

        ResponseEntity<?> response = confirmationController.confirmEmail(token);

        verify(confirmationService, times(1)).validateConfirmationToken(token);
        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
        assertEquals(URI.create("/api/users/login"), response.getHeaders().getLocation());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("UT: getToken() should send conf message")
    void getToken() throws MessagingException {
        String email = "test@gmail.com";

        ResponseEntity<?> response = confirmationController.getToken(email);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
