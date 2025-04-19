package aidcompass.api.user.appointment;

import aidcompass.api.general.models.appointment.AppointmentResponseDto;

import java.util.List;

public interface UserAppointmentService {
    List<AppointmentResponseDto> getAllUserAppointments(Long id);
}
