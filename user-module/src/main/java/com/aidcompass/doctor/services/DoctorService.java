package com.aidcompass.doctor.services;

import com.aidcompass.doctor.contracts.DoctorDeleteService;
import com.aidcompass.doctor.contracts.DoctorReadService;
import com.aidcompass.general.interfaces.PersistService;
import com.aidcompass.doctor.models.DoctorDto;
import com.aidcompass.doctor.dto.PrivateDoctorResponseDto;
import com.aidcompass.doctor.dto.PublicDoctorResponseDto;

import java.util.UUID;


public interface DoctorService extends PersistService<DoctorDto, PrivateDoctorResponseDto>,
                                       DoctorReadService, DoctorDeleteService {

    PrivateDoctorResponseDto update(UUID id, DoctorDto doctorUpdateDto);

    boolean existsById(UUID id);

    long countByIsApproved(boolean approved);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);
}
