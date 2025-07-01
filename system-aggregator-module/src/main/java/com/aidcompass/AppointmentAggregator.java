package com.aidcompass;

import com.aidcompass.appointment.contracts.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentAggregator {

    private final AppointmentService appointmentService;


//    public List<ReminderAppointmentDto> toRemindList(int batchSize) {
//        List<Appo>
//    }
}
