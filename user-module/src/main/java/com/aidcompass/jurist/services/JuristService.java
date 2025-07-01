package com.aidcompass.jurist.services;

import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.jurist.contracts.JuristDeleteService;
import com.aidcompass.jurist.contracts.JuristReadService;
import com.aidcompass.jurist.models.JuristDto;
import com.aidcompass.jurist.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.dto.PublicJuristResponseDto;

import java.util.UUID;


public interface JuristService extends PersistService<JuristDto, PrivateJuristResponseDto>,
        JuristReadService, JuristDeleteService {

    PrivateJuristResponseDto update(UUID id, JuristDto dto);

    void approve(UUID id);

    boolean existsById(UUID id);

    long countByIsApproved(boolean approved);

    PrivateJuristResponseDto findPrivateById(UUID id);

    PublicJuristResponseDto findPublicById(UUID id);
}
