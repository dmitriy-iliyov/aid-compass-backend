package com.aidcompass.appointment;


import com.aidcompass.appointment.models.AppointmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserAppointmentService {
    List<AppointmentResponseDto> getAllUserAppointments(UUID id);
}
