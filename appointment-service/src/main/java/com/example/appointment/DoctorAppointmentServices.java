package com.example.appointment;

import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentRegistrationDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentResponseDto;
import aidcompass.api.doctor.appointment.models.dto.DoctorAppointmentUpdateDto;

import java.util.List;

public interface DoctorAppointmentServices {

    void save(DoctorAppointmentRegistrationDto doctorAppointmentRegistrationDto) throws IllegalArgumentException;

    void update(DoctorAppointmentUpdateDto doctorAppointmentUpdateDto);

    boolean existingByDoctorNUserId(Long userId, Long doctorId);

    List<DoctorAppointmentResponseDto> findAllByDoctorId(Long id);

    List<DoctorAppointmentResponseDto> findAllByUserId(Long id);

    DoctorAppointmentResponseDto findById(Long id);

    void deleteById(Long id);

}
