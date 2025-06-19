package com.aidcompass.doctor.services;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;

import java.util.UUID;

public interface PersistDoctorService {
    PrivateDoctorResponseDto save(UUID id, DoctorDto doctorDTO, DetailEntity detail);
}
