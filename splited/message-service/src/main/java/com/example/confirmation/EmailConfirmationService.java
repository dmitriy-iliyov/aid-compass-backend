package com.example.confirmation;


import com.example.MessageDto;
import com.example.MessageService;
import com.example.exceptions.models.InvalidConfirmationTokenException;
import com.example.rest_clients.UserService;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Log4j2
@Service
public class EmailConfirmationService implements ConfirmationService {

    @Value("${aid.compass.messaging.api.confirm.email.url}")
    private String URL;

    private final MessageService messageService;
    private final UserService userService;
    private final ConfirmationRepository confirmationRepository;


    public EmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService, UserService userService,
                                    ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.userService = userService;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void sendConfirmationMessage(String recipientResource) {

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
                .orElseThrow(InvalidConfirmationTokenException::new);

        userService.confirmByEmail(email);
    }
}
