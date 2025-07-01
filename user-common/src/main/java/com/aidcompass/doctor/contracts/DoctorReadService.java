package com.aidcompass.doctor.contracts;

import com.aidcompass.PageResponse;
import com.aidcompass.doctor.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.dto.FullPrivateDoctorResponseDto;
import com.aidcompass.doctor.dto.FullPublicDoctorResponseDto;
import com.aidcompass.doctor.specialization.DoctorSpecialization;
import com.aidcompass.enums.gender.Gender;

import java.util.UUID;

public interface DoctorReadService {
    PageResponse<PublicDoctorResponseDto> findAllByNamesCombination(String firstName, String secondName, String lastName, int page, int size);

    FullPrivateDoctorResponseDto findFullPrivateById(UUID id);

    FullPublicDoctorResponseDto findFullPublicById(UUID id);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapproved(int page, int size);

    PageResponse<FullPrivateDoctorResponseDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                   String lastName, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllApproved(int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllBySpecialization(DoctorSpecialization specialization, int page, int size);

    PageResponse<PublicDoctorResponseDto> findAllByGender(Gender gender, int page, int size);

}
