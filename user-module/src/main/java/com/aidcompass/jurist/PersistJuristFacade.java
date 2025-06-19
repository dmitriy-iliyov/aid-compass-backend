package com.aidcompass.jurist;

import com.aidcompass.jurist.models.dto.jurist.JuristDto;
import com.aidcompass.jurist.models.dto.jurist.PrivateJuristResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PersistJuristFacade {
    PrivateJuristResponseDto save(UUID userId, JuristDto dto, HttpServletResponse response);
}
