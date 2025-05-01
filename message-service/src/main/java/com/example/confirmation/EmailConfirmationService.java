package com.example.confirmation;


import com.example.message.models.MessageDto;
import com.example.message.MessageService;
import com.example.exceptions.models.BaseInvalidConfirmationTokenException;
import com.example.clients.AuthService;
import io.jsonwebtoken.lang.Strings;
import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Service
@Log4j2
public class EmailConfirmationService implements ConfirmationService {

    @Value("${aid.compass.api.confirm.email.url}")
    private String URL;

    private final MessageService messageService;
    private final AuthService authService;
    private final ConfirmationRepository confirmationRepository;


    public EmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService, AuthService authService,
                                    ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.authService = authService;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void sendConfirmationMessage(String recipientResource) throws MessagingException {

        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        MessageDto message = new MessageDto(recipientResource, "Email confirming.", URL + encodedToken);

        messageService.sendMessage(message);
        confirmationRepository.save(token, recipientResource);
    }

    @Override
    public void validateConfirmationToken(String inputToken) {

        String decodedToken = Strings.ascii(Base64.getUrlDecoder().decode(inputToken.getBytes()));
        UUID token = UUID.fromString(decodedToken);

        String email = confirmationRepository.findAndDeleteByToken(token)
                .orElseThrow(BaseInvalidConfirmationTokenException::new);

        authService.confirmByEmail(email);
    }
}
