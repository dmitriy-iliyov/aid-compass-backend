package aidcompass.api.mail;

import com.example.clickerapi.services.mail.models.MessageDto;

public interface MessageService {

    void sendMessage(MessageDto messageDto);

}
