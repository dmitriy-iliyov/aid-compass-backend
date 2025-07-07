package com.aidcompass.doctor.services;

import com.aidcompass.doctor.models.dto.*;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.gender.Gender;
import com.aidcompass.general.contracts.dto.PageResponse;
import com.aidcompass.general.interfaces.PersistService;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface DoctorService extends PersistService<DoctorDto, PrivateDoctorResponseDto> {

    long countByIsApproved(boolean approved);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);

    FullPrivateDoctorResponseDto findFullPrivateById(UUID id);

    FullPublicDoctorResponseDto findFullPublicById(UUID id);

    PageResponse<PublicDoctorResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName, int page, int size);

    List<PublicDoctorResponseDto> findAllByIdIn(Set<UUID> ids);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapproved(int page, int size);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                   String lastName, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllApproved(int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllBySpecialization(DoctorSpecialization specialization, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllByGender(Gender gender, int page, int size);

    PrivateDoctorResponseDto update(UUID id, DoctorDto doctorUpdateDto);

    void deleteById(UUID id);

}
