package com.aidcompass.message;

import com.aidcompass.exceptions.models.SmsSendingException;
import com.aidcompass.message.models.MessageDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class SmsMessageService implements MessageService {

    @Value("${message_service.phone_number}")
    private String companyPhoneNumber;

    @Value("${message_service.twilio.account_sid}")
    private String accountSid;

    @Value("${message_service.twilio.auth_token}")
    private String authToken;


//    public SmsMessageService() {
//        Twilio.init(accountSid, authToken);
//    }

    @Async
    @Override
    public void sendMessage(MessageDto messageDto) {
        Message message = Message.creator(
                new PhoneNumber(messageDto.recipient()),
                new PhoneNumber(companyPhoneNumber),
                messageDto.subject() + " " + messageDto.text()
        ).create();

        if (Message.Status.QUEUED != message.getStatus()) {
            throw new SmsSendingException();
        }
    }
}
