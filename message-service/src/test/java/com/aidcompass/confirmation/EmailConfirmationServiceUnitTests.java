package com.aidcompass.confirmation;

import com.aidcompass.clients.AuthService;
import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message.MessageService;
import com.aidcompass.message.models.MessageDto;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class EmailConfirmationServiceUnitTests {

    static String URL = "https://localhost:8443/api/users/confirm/email?token=";

    @Mock
    ConfirmationRepository confirmationRepository;

    @Mock
    MessageService messageService;

    @Mock
    AuthService authService;

    @InjectMocks
    EmailConfirmationService emailConfirmationService;


    @Test
    @DisplayName("UT: sendConfirmationMessage() should send message with URL and token")
    void sendConfirmationMessage_shouldSendMessage() throws MessagingException {
        ReflectionTestUtils.setField(emailConfirmationService, "URL", URL);
        String resource = "test@gmail.com";

        emailConfirmationService.sendConfirmationMessage(resource);

        ArgumentCaptor<MessageDto> messageDtoArgumentCaptor = ArgumentCaptor.forClass(MessageDto.class);
        ArgumentCaptor<UUID> tokenCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(messageService).sendMessage(messageDtoArgumentCaptor.capture());
        verify(confirmationRepository).save(tokenCaptor.capture(), eq(resource));

        assertEquals(messageDtoArgumentCaptor.getValue().recipient(), resource);
        assertTrue(messageDtoArgumentCaptor.getValue().text().contains(URL));

        String encodedToken = Base64.getUrlEncoder().encodeToString(tokenCaptor.getValue().toString().getBytes());
        assertTrue(messageDtoArgumentCaptor.getValue().text().contains(encodedToken));

        verify(messageService, times(1)).sendMessage(any(MessageDto.class));
        verify(confirmationRepository, times(1)).save(any(UUID.class), any(String.class));
        verifyNoMoreInteractions(authService, confirmationRepository);
    }

    @Test
    @DisplayName("UT: validateConfirmationToken() should confirm user by email when token is valid")
    void validateConfirmationToken_shouldConfirm() {
        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());
        String email = "test@gmail.com";

        doReturn(Optional.of(email)).when(confirmationRepository).findAndDeleteByToken(token);

        emailConfirmationService.validateConfirmationToken(encodedToken);

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(confirmationRepository).findAndDeleteByToken(uuidArgumentCaptor.capture());
        assertEquals(token, uuidArgumentCaptor.getValue());

        verify(confirmationRepository, times(1)).findAndDeleteByToken(uuidArgumentCaptor.getValue());
        verify(authService, times(1)).confirmByEmail(email);
        verifyNoMoreInteractions(confirmationRepository, authService);
    }

    @Test
    @DisplayName("UT: validateConfirmationToken() should throw InvalidConfirmationTokenException when token is invalid or expired")
    void validateConfirmationToken_shouldThrowException() {
        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());
        String email = "test@gmail.com";

        doReturn(Optional.empty()).when(confirmationRepository).findAndDeleteByToken(token);

        assertThrows(InvalidConfirmationTokenException.class, () -> emailConfirmationService.validateConfirmationToken(encodedToken));

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(confirmationRepository).findAndDeleteByToken(uuidArgumentCaptor.capture());
        assertEquals(token, uuidArgumentCaptor.getValue());

        verify(confirmationRepository, times(1)).findAndDeleteByToken(uuidArgumentCaptor.getValue());
        verify(authService, times(0)).confirmByEmail(email);
        verifyNoMoreInteractions(confirmationRepository, authService);
    }
}
