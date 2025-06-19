package com.aidcompass.contracts;

public interface InformationService {

    void onScheduleNotification(ScheduledAppointmentInfo info);

    void onCancelNotification(CanceledAppointmentInfo info);
}
