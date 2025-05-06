package com.aidcompass.recovery;

import com.aidcompass.exceptions.models.InvalidPasswordRecoveryTokenException;
import com.aidcompass.message.models.MessageDto;
import com.aidcompass.message.MessageService;
import com.aidcompass.clients.AuthService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Service
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    @Value("${api.pass.recovery.email.uri}")
    private String URI;

    private final MessageService messageService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final AuthService authService;


    public EmailPasswordRecoveryService(@Qualifier("emailMessageService") MessageService messageService,
                                        PasswordRecoveryRepository passwordRecoveryRepository,
                                        AuthService authService) {
        this.messageService = messageService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.authService = authService;
    }

    @Override
    public void sendRecoveryMessage(String recipientResource) throws Exception {
        UUID token = UUID.randomUUID();
        String encodeToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        messageService.sendMessage(new MessageDto(recipientResource, "Password recovering.",
                URI + encodeToken));

        passwordRecoveryRepository.save(token, recipientResource);
    }

    @Override
    public void recoverPassword(String inputToken, String password) {
        String decodeInput = Strings.ascii(Base64.getUrlDecoder().decode(inputToken.getBytes()));
        UUID token = UUID.fromString(decodeInput);

        String email = passwordRecoveryRepository.findAndDeleteByToken(token).orElseThrow(
                InvalidPasswordRecoveryTokenException::new
        );

        authService.recoverPassword(email, password);
    }
}
