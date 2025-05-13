package com.aidcompass.doctor.services;

import com.aidcompass.detail.models.dto.PublicDetailResponseDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;

import java.util.List;
import java.util.UUID;


public interface DoctorService {

    PrivateDoctorResponseDto update(UUID id, DoctorUpdateDto doctorUpdateDto);

    String setAddress(UUID id, String address);

    void approve(UUID id);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);

    List<PrivateDoctorResponseDto> findAllUnapproved();

    List<PublicDoctorResponseDto> findAllApproved();

//    List<PublicDetailResponseDto> findAllApprovedBySpecialization(String specialization);

    void deleteById(UUID id);
}
