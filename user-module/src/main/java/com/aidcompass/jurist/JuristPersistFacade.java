package com.aidcompass.jurist;

import com.aidcompass.general.interfaces.PersistFacade;
import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.contracts.AuthService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.enums.Authority;
import com.aidcompass.jurist.models.JuristDto;
import com.aidcompass.jurist.dto.PrivateJuristResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JuristPersistFacade implements PersistFacade<JuristDto, PrivateJuristResponseDto> {

    private final PersistService<JuristDto, PrivateJuristResponseDto> juristService;
    private final PersistEmptyDetailService detailService;
    private final AuthService authService;


    @Transactional
    @Override
    public PrivateJuristResponseDto save(UUID userId, JuristDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(userId);
        PrivateJuristResponseDto responseDto = juristService.save(userId, detail, dto);
        authService.changeAuthorityById(userId, Authority.ROLE_JURIST, response);
        return responseDto;
    }
}
