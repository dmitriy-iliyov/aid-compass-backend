package com.aidcompass.jurist.contracts;

import com.aidcompass.PageResponse;
import com.aidcompass.enums.gender.Gender;
import com.aidcompass.jurist.dto.FullPrivateJuristResponseDto;
import com.aidcompass.jurist.dto.FullPublicJuristResponseDto;
import com.aidcompass.jurist.dto.PrivateJuristResponseDto;
import com.aidcompass.jurist.dto.PublicJuristResponseDto;

import java.util.UUID;

public interface JuristReadService {

    PrivateJuristResponseDto findPrivateById(UUID id);

    PublicJuristResponseDto findPublicById(UUID id);

    FullPrivateJuristResponseDto findFullPrivateById(UUID id);

    FullPublicJuristResponseDto findFullPublicById(UUID id);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndSpecialization(String type, String specialization,
                                                                         int page, int size);

    PageResponse<PublicJuristResponseDto> findAllByTypeAndNamesCombination(String type,
                                                                           String firstName, String secondName, String lastName,
                                                                           int page, int size);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapproved(int page, int size);

    PageResponse<FullPrivateJuristResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                   String lastName, int page, int size);

    PageResponse<PublicJuristResponseDto> findAllApproved(int page, int size);

    PageResponse<PublicJuristResponseDto> findAllByGender(Gender gender, int page, int size);
}
