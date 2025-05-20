package com.aidcompass.doctor;

import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;

import java.util.UUID;

public interface PersistFacade {
    PrivateDoctorResponseDto save(UUID userId, DoctorRegistrationDto dto);
}
