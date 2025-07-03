package com.aidcompass.confirmation.services;

import com.aidcompass.ContactType;
import com.aidcompass.confirmation.repositories.ConfirmationRepository;
import com.aidcompass.contact.core.facades.SystemContactFacade;
import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message_services.MessageFactory;
import com.aidcompass.message_services.MessageService;
import com.aidcompass.utils.CodeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aidcompass.general.GlobalRedisConfig.CONF_TOKEN_KEY_TEMPLATE;


@Service
@Slf4j
public class PhoneNumberConfirmationService implements ResourceConfirmationService {

    private final ContactType type = ContactType.PHONE_NUMBER;

    private final MessageService messageService;
    private final SystemContactFacade systemContactFacade;

    @Value("${api.conf.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "phone_number:%s";
    private final ConfirmationRepository confirmationRepository;


    public PhoneNumberConfirmationService(@Qualifier("smsMessageService") MessageService messageService,
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
    public void validateConfirmationToken(String token) {
        String resourceId = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(token))
                .orElseThrow(InvalidConfirmationTokenException::new);

        systemContactFacade.confirmContactById(Long.valueOf(resourceId));
    }
}
