package com.example.recovery;

import com.example.MessageDto;
import com.example.MessageService;
import com.example.exceptions.models.InvalidPasswordRecoveryTokenException;
import com.example.rest_clients.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailPasswordRecoveryServiceUnitTests {

    private static final String URL = "https://localhost:8443/api/users/password/recover?token=";

    @Mock
    private MessageService messageService;

    @Mock
    private PasswordRecoveryRepository passwordRecoveryRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmailPasswordRecoveryService recoveryService;


    @Test
    @DisplayName("UT: sendRecoveryMessage() should send message with encoded token and save it")
    void sendRecoveryMessage_shouldSendMessage() {
        ReflectionTestUtils.setField(recoveryService, "URL", URL);
        String email = "test@gmail.com";

        recoveryService.sendRecoveryMessage(email);

        ArgumentCaptor<MessageDto> messageCaptor = ArgumentCaptor.forClass(MessageDto.class);
        ArgumentCaptor<UUID> tokenCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(messageService).sendMessage(messageCaptor.capture());
        verify(passwordRecoveryRepository).save(tokenCaptor.capture(), eq(email));

        MessageDto sentMessage = messageCaptor.getValue();
        UUID token = tokenCaptor.getValue();

        assertEquals(email, sentMessage.recipient());
        assertTrue(sentMessage.text().contains(URL));

        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());
        assertTrue(sentMessage.text().contains(encodedToken));

        verifyNoMoreInteractions(messageService, passwordRecoveryRepository, userService);
    }

    @Test
    @DisplayName("UT: recoverPassword() should recover password when token is valid")
    void recoverPassword_shouldRecoverPassword() {
        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());
        String email = "user@example.com";
        String newPassword = "newSecurePassword";

        when(passwordRecoveryRepository.findAndDeleteByToken(token)).thenReturn(Optional.of(email));

        recoveryService.recoverPassword(encodedToken, newPassword);

        verify(passwordRecoveryRepository).findAndDeleteByToken(token);
        verify(userService).recoverPassword(email, newPassword);
        verifyNoMoreInteractions(passwordRecoveryRepository, userService, messageService);
    }

    @Test
    @DisplayName("UT: recoverPassword() should throw exception when token is invalid")
    void recoverPassword_shouldThrowException_whenTokenInvalid() {
        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());
        String newPassword = "newSecurePassword";

        when(passwordRecoveryRepository.findAndDeleteByToken(token)).thenReturn(Optional.empty());

        assertThrows(InvalidPasswordRecoveryTokenException.class, () ->
                recoveryService.recoverPassword(encodedToken, newPassword));

        verify(passwordRecoveryRepository).findAndDeleteByToken(token);
        verifyNoInteractions(userService);
        verifyNoMoreInteractions(passwordRecoveryRepository, messageService);
    }
}