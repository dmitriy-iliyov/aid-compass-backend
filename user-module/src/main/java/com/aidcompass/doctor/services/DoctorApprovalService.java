package com.aidcompass.doctor.services;

import com.aidcompass.general.contracts.dto.BaseSystemVolunteerDto;

import java.util.UUID;

public interface DoctorApprovalService {
    BaseSystemVolunteerDto approve(UUID id);
}
