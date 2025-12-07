package com.aidcompass.users.doctor.services;

import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.users.doctor.models.dto.*;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.general.PersistService;
import com.aidcompass.users.general.dto.NameFilter;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface DoctorService extends PersistService<DoctorDto, PrivateDoctorResponseDto> {

    long countByIsApproved(boolean approved);

    PrivateDoctorResponseDto findPrivateById(UUID id);

    PublicDoctorResponseDto findPublicById(UUID id);

    FullPrivateDoctorResponseDto findFullPrivateById(UUID id);

    FullPublicDoctorResponseDto findFullPublicById(UUID id);

    PageResponse<PublicDoctorResponseDto> findAllByNamesCombination(NameFilter filter);

    List<PublicDoctorResponseDto> findAllByIdIn(Set<UUID> ids);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapproved(PageRequest page);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapprovedByNamesCombination(NameFilter filter);

    PageResponse<PublicDoctorResponseDto> findAllApproved(PageRequest page);

    PageResponse<PublicDoctorResponseDto> findAllBySpecialization(DoctorSpecializationFilter filter);

    PageResponse<PublicDoctorResponseDto> findAllByGender(Gender gender, PageRequest page);

    PrivateDoctorResponseDto update(UUID id, DoctorDto doctorUpdateDto);

    void deleteById(UUID id);

}
