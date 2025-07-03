package com.aidcompass.message_services;

import com.aidcompass.message_services.models.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class SmsMessageService implements MessageService {


    @Override
    public void sendMessage(MessageDto messageDto) throws Exception {

    }

//    @Value("${message_service.phone_number}")
//    private String companyPhoneNumber;
//
//    @Value("${message_service.twilio.account_sid}")
//    private String accountSid;
//
//    @Value("${message_service.twilio.auth_token}")
//    private String authToken;
//
//
//    public SmsMessageService() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    @Async
//    @Override
//    public void sendMessage(MessageDto messageDto) {
//        Message message = Message.creator(
//                new PhoneNumber(messageDto.recipient()),
//                new PhoneNumber(companyPhoneNumber),
//                messageDto.subject() + " " + messageDto.text()
//        ).create();
//
//        if (Message.Status.QUEUED != message.getStatus()) {
//            throw new SmsSendException();
//        }
//    }
}
