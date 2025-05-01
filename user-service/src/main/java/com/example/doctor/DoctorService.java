package com.example.doctor;

import aidcompass.api.doctor.models.dto.DoctorRegistrationDto;
import aidcompass.api.doctor.models.dto.DoctorResponseDto;
import aidcompass.api.doctor.models.dto.DoctorUpdateDto;

import java.util.List;

public interface DoctorService {

    void save(DoctorRegistrationDto doctorRegistrationDTO, Long userId);

    DoctorUpdateDto mapToUpdateDto(DoctorRegistrationDto doctorRegistrationDto);

    void update(DoctorUpdateDto doctorUpdateDto);

    void approve(Long id);

    boolean existingById(Long id);

    DoctorResponseDto findById(Long id);

    DoctorResponseDto findByUsername(String username);

    List<DoctorResponseDto> findAllUnapproved();

    List<DoctorResponseDto> findAllApproved();

    List<DoctorResponseDto> findAllApprovedBySpecialization(String specialization);

    void deleteById(Long id);
}
