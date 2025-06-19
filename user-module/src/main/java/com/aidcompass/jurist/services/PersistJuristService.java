package com.aidcompass.jurist.services;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.jurist.models.dto.jurist.JuristDto;
import com.aidcompass.jurist.models.dto.jurist.PrivateJuristResponseDto;

import java.util.UUID;

public interface PersistJuristService {
    PrivateJuristResponseDto save(UUID id, JuristDto doctorRegistrationDTO, DetailEntity detail);
}
