package com.aidcompass.doctor.services;

import com.aidcompass.dto.BaseSystemVolunteerDto;

import java.util.UUID;

public interface DoctorApprovalService {
    BaseSystemVolunteerDto approve(UUID id);
}
