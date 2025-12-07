package com.aidcompass.users.jurist.services;

import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.general.PersistService;
import com.aidcompass.users.general.dto.NameFilter;
import com.aidcompass.users.jurist.models.dto.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface JuristService extends PersistService<JuristDto, PrivateJuristResponseDto> {

    long countByIsApproved(boolean approved);

    PrivateJuristResponseDto findPrivateById(UUID id);

    PublicJuristResponseDto findPublicById(UUID id);

    FullPrivateJuristResponseDto findFullPrivateById(UUID id);

    FullPublicJuristResponseDto findFullPublicById(UUID id);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndSpecialization(JuristSpecializationFilter filter);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndNamesCombination(JuristNameFilter filter);

    List<PublicJuristResponseDto> findAllByIdIn(Set<UUID> ids);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapproved(PageRequest page);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapprovedByNamesCombination(NameFilter filter);

    PageResponse<PublicJuristResponseDto> findAllApproved(PageRequest page);

    PageResponse<PublicJuristResponseDto> findAllByFilter(String type, String specialization,
                                                          String firstName, String secondName,
                                                          String lastName, int page, int size);

    PageResponse<PublicJuristResponseDto> findAllByGender(Gender gender, PageRequest page);

    PrivateJuristResponseDto update(UUID id, JuristDto dto);

    BaseSystemVolunteerDto approve(UUID id);

    void deleteById(UUID id);
}
