package com.aidcompass.confirmation.services;


import com.aidcompass.clients.AuthService;
import com.aidcompass.confirmation.repositories.ConfirmationRepository;
import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message.MessageService;
import com.aidcompass.message.models.MessageDto;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

import static com.aidcompass.configs.RedisConfig.CONF_TOKEN_KEY_TEMPLATE;

@Service
public class AccountEmailConfirmationService implements AccountResourceConfirmationService {

    @Value("${api.conf.account.email.uri}")
    private String URI;

    private final MessageService messageService;
    private final AuthService authService;

    @Value("${api.conf.account.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "account_email:%s";
    private final ConfirmationRepository confirmationRepository;


    public AccountEmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService,
                                           AuthService authService,
                                           ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.authService = authService;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void sendConfirmationMessage(String resource) throws Exception {

        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        MessageDto message = new MessageDto(resource, "Account email confirming.", URI + encodedToken);

        confirmationRepository.save(KEY_TEMPLATE.formatted(token), resource, TOKEN_TTL);
        messageService.sendMessage(message);
    }

    @Override
    public void validateConfirmationToken(String token) {

        String decodedString = Strings.ascii(Base64.getUrlDecoder().decode(token.getBytes()));
        UUID decodedToken = UUID.fromString(decodedString);

        String email = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(decodedToken)).orElseThrow(
                InvalidConfirmationTokenException::new
        );

        authService.confirmByEmail(email);
    }
}
