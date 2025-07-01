package com.aidcompass.doctor.services;

import com.aidcompass.doctor.mapper.DoctorMapper;
import com.aidcompass.doctor.repository.DoctorRepository;
import com.aidcompass.dto.BaseSystemVolunteerDto;
import com.aidcompass.general.exceptions.doctor.DoctorNotFoundByIdException;
import com.aidcompass.jurist.models.JuristEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultDoctorApprovalService implements DoctorApprovalService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;


    @Transactional
    @Override
    public BaseSystemVolunteerDto approve(UUID id) {
        repository.approveById(id);
        return mapper.toSystemDto(repository.findById(id).orElseThrow(DoctorNotFoundByIdException::new));
    }
}
