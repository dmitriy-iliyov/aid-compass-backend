package com.aidcompass.jurist;

import com.aidcompass.general.interfaces.PersistFacade;
import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.jurist.models.dto.JuristDto;
import com.aidcompass.jurist.models.dto.PrivateJuristResponseDto;
import com.aidcompass.security.auth.services.UserAuthService;
import com.aidcompass.security.domain.authority.models.Authority;
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
    private final UserAuthService userAuthService;


    @Transactional
    @Override
    public PrivateJuristResponseDto save(UUID userId, JuristDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(userId);
        PrivateJuristResponseDto responseDto = juristService.save(userId, detail, dto);
        userAuthService.changeAuthorityById(userId, Authority.ROLE_JURIST, response);
        return responseDto;
    }
}
