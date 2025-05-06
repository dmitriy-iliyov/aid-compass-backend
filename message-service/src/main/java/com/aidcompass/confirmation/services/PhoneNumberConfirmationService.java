package com.aidcompass.confirmation.services;

import com.aidcompass.clients.ContactService;
import com.aidcompass.confirmation.ResourceType;
import com.aidcompass.confirmation.repositories.ConfirmationRepository;
import com.aidcompass.confirmation.utils.CodeFactory;
import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message.MessageService;
import com.aidcompass.message.models.MessageDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aidcompass.configs.RedisConfig.CONF_TOKEN_KEY_TEMPLATE;

@Service
public class PhoneNumberConfirmationService implements ResourceConfirmationService {

    private final ResourceType type = ResourceType.PHONE_NUMBER;

    private final MessageService messageService;
    private final ContactService contactService;
    private final CodeFactory codeFactory;

    @Value("${api.conf.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "phone_number:%s";
    private final ConfirmationRepository confirmationRepository;


    public PhoneNumberConfirmationService(@Qualifier("smsMessageService") MessageService messageService,
                                          ContactService contactService,
                                          ConfirmationRepository confirmationRepository,
                                          CodeFactory codeFactory) {
        this.messageService = messageService;
        this.contactService = contactService;
        this.confirmationRepository = confirmationRepository;
        this.codeFactory = codeFactory;
    }

    @Override
    public String getType() {
        return type.toString();
    }

    @Override
    public void sendConfirmationMessage(String resource, Long resourceId) throws Exception {
        String code = codeFactory.generate();
        MessageDto message = new MessageDto(resource, "Confirmation phone number", code);

        confirmationRepository.save(KEY_TEMPLATE.formatted(code), resourceId.toString(), TOKEN_TTL);
        messageService.sendMessage(message);
    }

    @Override
    public void validateConfirmationToken(String token) {
        String resourceId = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(token))
                .orElseThrow(InvalidConfirmationTokenException::new);

        contactService.confirmContactById(Long.valueOf(resourceId));
    }
}
