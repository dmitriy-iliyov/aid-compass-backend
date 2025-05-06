package com.aidcompass.message;

import com.aidcompass.message.models.MessageDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;


@Service
public class TelegramMessageService implements MessageService {


    @Override
    public void sendMessage(MessageDto messageDto) {

    }
}
