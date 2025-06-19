package com.aidcompass.doctor.services;

import com.aidcompass.PageResponse;
import com.aidcompass.detail.models.Gender;
import com.aidcompass.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.DoctorDto;
import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
import com.aidcompass.doctor.models.dto.doctor.PublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


public interface DoctorService extends PersistDoctorService {

    PrivateDoctorResponseDto update(UUID id, DoctorDto doctorUpdateDto);

    boolean existsById(UUID id);

    PageResponse<PublicDoctorResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName, int page, int size);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);

    FullPrivateDoctorResponseDto findFullPrivateById(UUID id);

    FullPublicDoctorResponseDto findFullPublicById(UUID id);

    long countByIsApproved(boolean approved);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapproved(int page, int size);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                   String lastName, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllApproved(int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllBySpecialization(DoctorSpecialization specialization, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllByGender(Gender gender, int page, int size);

    void deleteById(UUID id);
}
