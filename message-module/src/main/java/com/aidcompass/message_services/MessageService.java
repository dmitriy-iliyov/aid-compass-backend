package com.aidcompass.message_services;

import com.aidcompass.message_services.models.MessageDto;

public interface MessageService {

    void sendMessage(MessageDto messageDto) throws Exception;

}
