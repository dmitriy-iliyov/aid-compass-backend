package com.aidcompass.jurist;

import com.aidcompass.contracts.AuthService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.enums.Authority;
import com.aidcompass.jurist.models.dto.jurist.JuristDto;
import com.aidcompass.jurist.models.dto.jurist.PrivateJuristResponseDto;
import com.aidcompass.jurist.services.PersistJuristService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersistJuristFacadeImpl implements PersistJuristFacade {

    private final PersistJuristService juristService;
    private final PersistEmptyDetailService detailService;
    private final AuthService authService;


    @Transactional
    @Override
    public PrivateJuristResponseDto save(UUID userId, JuristDto dto, HttpServletResponse response) {
        DetailEntity entity = detailService.saveEmpty(userId);
        PrivateJuristResponseDto responseDto = juristService.save(userId, dto, entity);
        authService.changeAuthorityById(userId, Authority.ROLE_JURIST, response);
        return responseDto;
    }
}
