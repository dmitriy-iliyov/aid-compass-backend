package com.example.appointment;


import com.example.appointment.models.AppointmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserAppointmentService {
    List<AppointmentResponseDto> getAllUserAppointments(UUID id);
}
