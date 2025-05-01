package com.example.message;

import com.example.message.models.MessageDto;
import jakarta.mail.MessagingException;

public interface MessageService {

    void sendMessage(MessageDto messageDto) throws MessagingException;

}
