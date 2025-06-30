package com.aidcompass.information;

import com.aidcompass.dto.CanceledAppointmentDto;
import com.aidcompass.contracts.InformationService;
import com.aidcompass.dto.ReminderAppointmentDto;
import com.aidcompass.dto.ScheduledAppointmentDto;
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
    public void reminderNotification(ReminderAppointmentDto dto) {
        try {
            messageService.sendMessage(MessageFactory.customerReminder(dto));
        } catch (Exception e) {
            throw new SendMessageException();
        }
    }

    @Override
    public void onScheduleNotification(ScheduledAppointmentDto dto) {
        try {
            messageService.sendMessage(MessageFactory.customerAppointmentScheduled(dto));
            messageService.sendMessage(MessageFactory.volunteerAppointmentScheduled(dto));
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new SendMessageException();
        }
    }

    @Override
    public void onCancelNotification(CanceledAppointmentDto dto) {

    }
}
