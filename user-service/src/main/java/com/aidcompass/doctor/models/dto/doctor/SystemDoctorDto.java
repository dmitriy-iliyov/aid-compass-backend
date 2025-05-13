package com.aidcompass.doctor.models.dto.doctor;

import com.aidcompass.doctor.specialization.models.DoctorSpecialization;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record SystemDoctorDto(
        UUID id,
        String firstName,
        String secondName,
        String lastName,
        List<DoctorSpecialization> specializations,
        String address,
        String profileStatus,
        Instant createdAt,
        Instant updatesAt
) { }
