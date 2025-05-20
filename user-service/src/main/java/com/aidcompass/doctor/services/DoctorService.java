package com.aidcompass.doctor.services;

import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorUpdateDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.page.PageDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;

import java.util.List;
import java.util.UUID;


public interface DoctorService {

    PrivateDoctorResponseDto update(UUID id, DoctorUpdateDto doctorUpdateDto);

    void approve(UUID id);

    List<PublicDoctorResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName, PageDto page);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);

    FullPrivateDoctorResponseDto findFullPrivateById(UUID id);

    FullPublicDoctorResponseDto findFullPublicById(UUID id);

    List<PrivateDoctorResponseDto> findAllUnapproved(PageDto page);

    List<PublicDoctorResponseDto> findAllApproved(PageDto page);

    List<PublicDoctorResponseDto> findAllApprovedBySpecialization(DoctorSpecialization specialization, PageDto page);

    void deleteById(UUID id);
}
