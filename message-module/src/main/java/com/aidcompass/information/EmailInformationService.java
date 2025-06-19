package com.aidcompass.information;

import com.aidcompass.contracts.CanceledAppointmentInfo;
import com.aidcompass.contracts.InformationService;
import com.aidcompass.contracts.ScheduledAppointmentInfo;
import com.aidcompass.exceptions.models.SendMessageException;
import com.aidcompass.message_services.MessageFactory;
import com.aidcompass.message_services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class EmailInformationService implements InformationService {

    private final MessageService messageService;


    public EmailInformationService(@Qualifier("emailMessageService") MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onScheduleNotification(ScheduledAppointmentInfo info) {
        try {
            messageService.sendMessage(MessageFactory.customerAppointmentScheduled(info));
            messageService.sendMessage(MessageFactory.volunteerAppointmentScheduled(info));
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new SendMessageException();
        }
    }

    @Override
    public void onCancelNotification(CanceledAppointmentInfo info) {

    }
}
