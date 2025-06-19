package com.aidcompass.doctor.services;

import com.aidcompass.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultDoctorApprovalService implements DoctorApprovalService {

    private final DoctorRepository repository;


    @Transactional
    @Override
    public void approve(UUID id) {
        repository.approveById(id);
    }
}
