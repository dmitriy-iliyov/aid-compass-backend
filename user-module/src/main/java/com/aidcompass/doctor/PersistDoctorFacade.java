package com.aidcompass.doctor;

import com.aidcompass.doctor.models.dto.doctor.DoctorDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface PersistDoctorFacade {
    PrivateDoctorResponseDto save(UUID userId, DoctorDto dto, HttpServletResponse response);
}
