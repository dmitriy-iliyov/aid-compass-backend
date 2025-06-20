package com.aidcompass.jurist.services;

import com.aidcompass.PageResponse;
import com.aidcompass.detail.models.Gender;
import com.aidcompass.jurist.models.dto.FullPrivateJuristResponseDto;
import com.aidcompass.jurist.models.dto.FullPublicJuristResponseDto;
import com.aidcompass.jurist.models.dto.jurist.JuristDto;
import com.aidcompass.jurist.models.dto.jurist.PrivateJuristResponseDto;
import com.aidcompass.jurist.models.dto.jurist.PublicJuristResponseDto;

import java.util.UUID;


public interface JuristService extends PersistJuristService {

    PrivateJuristResponseDto update(UUID id, JuristDto dto);

    void approve(UUID id);

    boolean existsById(UUID id);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndSpecialization(String type, String specialization,
                                                                         int page, int size);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndNamesCombination(String type,
                                                                           String firstName, String secondName, String lastName,
                                                                           int page, int size);

    PrivateJuristResponseDto findPrivateById(UUID id);

    PublicJuristResponseDto findPublicById(UUID id);

    FullPrivateJuristResponseDto findFullPrivateById(UUID id);

    FullPublicJuristResponseDto findFullPublicById(UUID id);

    long countByIsApproved(boolean approved);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapproved(int page, int size);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                   String lastName, int page, int size);

    PageResponse<PublicJuristResponseDto> findAllApproved(int page, int size);

    PageResponse<PublicJuristResponseDto> findAllByGender(Gender gender, int page, int size);

    void deleteById(UUID id);
}
