package com.aidcompass.message;

import com.aidcompass.message.models.MessageDto;
import jakarta.mail.MessagingException;

public interface MessageService {

    void sendMessage(MessageDto messageDto) throws MessagingException;

}
