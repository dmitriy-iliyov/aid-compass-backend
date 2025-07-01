package com.aidcompass.doctor;

import com.aidcompass.general.interfaces.PersistFacade;
import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.contracts.AuthService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.models.DoctorDto;
import com.aidcompass.doctor.dto.PrivateDoctorResponseDto;
import com.aidcompass.enums.Authority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DoctorPersistFacade implements PersistFacade<DoctorDto, PrivateDoctorResponseDto> {

    private final PersistService<DoctorDto, PrivateDoctorResponseDto> doctorService;
    private final PersistEmptyDetailService detailService;
    private final AuthService authService;


    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID userId, DoctorDto dto, HttpServletResponse response) {
        DetailEntity detail = detailService.saveEmpty(userId);
        PrivateDoctorResponseDto responseDto = doctorService.save(userId, detail, dto);
        authService.changeAuthorityById(userId, Authority.ROLE_DOCTOR, response);
        return responseDto;
    }
}
