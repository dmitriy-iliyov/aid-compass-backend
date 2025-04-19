package com.example.recovery;

import com.example.MessageDto;
import com.example.MessageService;
import com.example.exceptions.models.InvalidPasswordRecoveryTokenException;
import com.example.rest_clients.UserService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Service
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    @Value("${aid.compass.messaging.api.password.recovery.email.url}")
    private String URL;

    private final MessageService messageService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserService userService;


    public EmailPasswordRecoveryService(@Qualifier("emailMessageService") MessageService messageService,
                                        PasswordRecoveryRepository passwordRecoveryRepository,
                                        UserService userService) {
        this.messageService = messageService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.userService = userService;
    }

    @Override
    public void sendRecoveryMessage(String recipientResource) {
        UUID token = UUID.randomUUID();
        String encodeToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        messageService.sendMessage(new MessageDto(recipientResource, "Password recovering.",
                URL + encodeToken));

        passwordRecoveryRepository.save(token, recipientResource);
    }

    @Override
    public void recoverPassword(String inputToken, String password) {
        String decodeInput = Strings.ascii(Base64.getUrlDecoder().decode(inputToken.getBytes()));
        UUID token = UUID.fromString(decodeInput);

        String email = passwordRecoveryRepository.findAndDeleteByToken(token).orElseThrow(
                InvalidPasswordRecoveryTokenException::new
        );

        userService.recoverPassword(email, password);
    }
}
