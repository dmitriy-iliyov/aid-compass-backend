package com.aidcompass.message.message;

import com.aidcompass.message.message.models.MessageDto;
import jakarta.mail.MessagingException;

public interface MessageService {

    void sendMessage(MessageDto messageDto) throws MessagingException;

}
