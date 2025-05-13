package com.aidcompass.doctor;

import com.aidcompass.detail.DetailService;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.doctor.models.dto.doctor.DoctorRegistrationDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.services.PersistDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DoctorFacadeImpl implements DoctorFacade {

    private final PersistDoctorService doctorService;
    private final DetailService detailService;


    @Transactional
    @Override
    public PrivateDoctorResponseDto save(UUID userId, DoctorRegistrationDto dto) {
        DetailEntity entity = detailService.saveEmpty(userId);
        return doctorService.save(userId, dto, entity);
    }
}
