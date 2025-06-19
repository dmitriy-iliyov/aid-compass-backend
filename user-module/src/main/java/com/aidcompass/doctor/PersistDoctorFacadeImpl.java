package com.aidcompass.doctor;

import com.aidcompass.contracts.AuthService;
import com.aidcompass.detail.PersistEmptyDetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.services.PersistDoctorService;
import com.aidcompass.enums.Authority;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersistDoctorFacadeImpl implements PersistDoctorFacade {

    private final PersistDoctorService doctorService;
    private final PersistEmptyDetailService detailService;
    private final AuthService authService;


    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID userId, DoctorDto dto, HttpServletResponse response) {
        DetailEntity entity = detailService.saveEmpty(userId);
        PrivateDoctorResponseDto responseDto = doctorService.save(userId, dto, entity);
        authService.changeAuthorityById(userId, Authority.ROLE_DOCTOR, response);
        return responseDto;
    }
}
