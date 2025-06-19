package com.aidcompass.confirmation.services;


import com.aidcompass.confirmation.repositories.ConfirmationRepository;
import com.aidcompass.contracts.SystemContactFacade;
import com.aidcompass.enums.ContactType;
import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message_services.MessageFactory;
import com.aidcompass.message_services.MessageService;
import com.aidcompass.utils.CodeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aidcompass.GlobalRedisConfig.CONF_TOKEN_KEY_TEMPLATE;


@Service
@Slf4j
public class EmailConfirmationService implements ResourceConfirmationService {

    private final ContactType type = ContactType.EMAIL;


    private final MessageService messageService;
    private final SystemContactFacade systemContactFacade;

    @Value("${api.conf.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "email:%s";
    private final ConfirmationRepository confirmationRepository;


    public EmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService,
                                    SystemContactFacade systemContactFacade,
                                    ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.systemContactFacade = systemContactFacade;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public String getType() {
        return type.toString();
    }

    @Override
    public void sendConfirmationMessage(String resource, Long resourceId) throws Exception {
        String code = CodeFactory.generate();
        confirmationRepository.save(KEY_TEMPLATE.formatted(code), resourceId.toString(), TOKEN_TTL);
        messageService.sendMessage(MessageFactory.resourceConfirmation(resource, code));
    }

    @Override
    public void validateConfirmationToken(String code) {
        String resourceId = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(code))
                .orElseThrow(InvalidConfirmationTokenException::new);
        systemContactFacade.confirmContactById(Long.valueOf(resourceId));
    }
}
