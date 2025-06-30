package com.aidcompass.information;

import com.aidcompass.contracts.GreetingService;
import com.aidcompass.dto.UserDto;
import com.aidcompass.exceptions.models.SendMessageException;
import com.aidcompass.message_services.MessageFactory;
import com.aidcompass.message_services.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EmailGreetingService implements GreetingService {

    private final MessageService messageService;


    public EmailGreetingService(@Qualifier("emailMessageService") MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onApproveNotification(UserDto dto) {
        try {
            messageService.sendMessage(MessageFactory.greeting(dto));
        } catch (Exception e) {
            throw new SendMessageException();
        }
    }
}
