package com.aidcompass.contracts;

import com.aidcompass.dto.AppointmentCanceledDto;
import com.aidcompass.dto.AppointmentReminderDto;
import com.aidcompass.dto.AppointmentScheduledDto;

import java.util.List;

public interface InformationService {
    void reminderNotification(List<AppointmentReminderDto> dtoList);

    void onScheduleNotification(AppointmentScheduledDto info);

    void onCancelNotification(AppointmentCanceledDto info);
}
