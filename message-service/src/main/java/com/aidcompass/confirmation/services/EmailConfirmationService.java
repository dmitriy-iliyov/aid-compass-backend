package com.aidcompass.confirmation.services;


import com.aidcompass.clients.ContactService;
import com.aidcompass.confirmation.ResourceType;
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
public class EmailConfirmationService implements ResourceConfirmationService {

    private final ResourceType type = ResourceType.EMAIL;

    @Value("${api.conf.email.uri}")
    private String URI;

    private final MessageService messageService;
    private final ContactService contactService;

    @Value("${api.conf.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "email:%s";
    private final ConfirmationRepository confirmationRepository;


    public EmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService,
                                    ContactService contactService,
                                    ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.contactService = contactService;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public String getType() {
        return type.toString();
    }

    @Override
    public void sendConfirmationMessage(String resource, Long resourceId) throws Exception {

        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        MessageDto message = new MessageDto(resource, "Email confirming.", URI + encodedToken);

        confirmationRepository.save(KEY_TEMPLATE.formatted(token), resourceId.toString(), TOKEN_TTL);
        messageService.sendMessage(message);
    }

    @Override
    public void validateConfirmationToken(String token) {

        String decodedString = Strings.ascii(Base64.getUrlDecoder().decode(token.getBytes()));
        UUID decodedToken = UUID.fromString(decodedString);

        String resourceId = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(decodedToken))
                .orElseThrow(InvalidConfirmationTokenException::new);

        contactService.confirmContactById(Long.valueOf(resourceId));
    }
}
