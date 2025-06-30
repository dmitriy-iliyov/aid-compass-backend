package com.aidcompass.contracts;

import com.aidcompass.dto.CanceledAppointmentDto;
import com.aidcompass.dto.ReminderAppointmentDto;
import com.aidcompass.dto.ScheduledAppointmentDto;

public interface InformationService {

    void reminderNotification(ReminderAppointmentDto dto);

    void onScheduleNotification(ScheduledAppointmentDto info);

    void onCancelNotification(CanceledAppointmentDto info);
}
