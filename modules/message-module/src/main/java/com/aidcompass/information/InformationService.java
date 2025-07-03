package com.aidcompass.information;


import com.aidcompass.information.dto.AppointmentCanceledDto;
import com.aidcompass.information.dto.AppointmentReminderDto;
import com.aidcompass.information.dto.AppointmentScheduledDto;

import java.util.List;

public interface InformationService {
    void reminderNotification(List<AppointmentReminderDto> dtoList);

    void onScheduleNotification(AppointmentScheduledDto info);

    void onCancelNotification(AppointmentCanceledDto info);
}
