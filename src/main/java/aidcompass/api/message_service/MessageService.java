package aidcompass.api.message_service;


import aidcompass.api.message_service.models.MessageDto;

public interface MessageService {

    void sendMessage(MessageDto messageDto);

}
