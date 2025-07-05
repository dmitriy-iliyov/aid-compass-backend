package com.aidcompass.api.doctor;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.api.doctor.dto.DoctorCardDto;
import com.aidcompass.api.doctor.dto.DoctorPrivateProfileDto;
import com.aidcompass.api.doctor.dto.DoctorPublicProfileDto;
import com.aidcompass.doctor.models.dto.*;
import com.aidcompass.doctor.services.DoctorService;
import com.aidcompass.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.gender.Gender;
import com.aidcompass.general.contracts.dto.PageResponse;
import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorAggregatorService {

    private final DoctorService doctorService;
    private final AggregatorUtils utils;


    public DoctorPublicProfileDto findPublicProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPublicDoctorResponseDto fullDto = doctorService.findFullPublicById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new DoctorPublicProfileDto(url, fullDto, utils.findAllContactByOwnerId(id), appointmentDuration);
    }

    public DoctorPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPrivateDoctorResponseDto fullDto = doctorService.findFullPrivateById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new DoctorPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id), appointmentDuration);
    }

    public PageResponse<DoctorCardDto> findAllApproved(int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllApproved(page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorPrivateProfileDto> findAllUnapproved(int page, int size) {
        PageResponse<FullPrivateDoctorResponseDto> doctors = doctorService.findAllUnapproved(page, size);
        return new PageResponse<>(
                this.aggregateToPrivate(doctors.data()),
                doctors.totalPage()
        );
    }

    public PageResponse<DoctorPrivateProfileDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                     String lastName,int page, int size) {
        PageResponse<FullPrivateDoctorResponseDto> doctors = doctorService.findAllUnapprovedByNamesCombination(firstName, secondName, lastName, page, size);
        return new PageResponse<>(
                this.aggregateToPrivate(doctors.data()),
                doctors.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllApprovedBySpecialization(DoctorSpecialization doctorSpecialization, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllBySpecialization(doctorSpecialization, page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllByNamesCombination(String firstName, String secondName, String lastName, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllByNamesCombination(firstName, secondName, lastName, page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<DoctorCardDto> findAllByGender(Gender gender, int page, int size) {
        PageResponse<PublicDoctorResponseDto> pageResponse = doctorService.findAllByGender(gender, page, size);
        return new PageResponse<>(
                aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    @Transactional(noRollbackFor = BaseNotFoundException.class)
    public void delete(UUID id) {
        utils.deleteAllUserAlignments(id);
        utils.deleteAllVolunteerAlignments(id);
        doctorService.deleteById(id);
    }

    private List<DoctorCardDto> aggregate(List<PublicDoctorResponseDto> dtoList) {
        List<DoctorCardDto> doctorCardDtoList = new ArrayList<>();

        List<UUID> uuids = new ArrayList<>();
        for (PublicDoctorResponseDto dto : dtoList) {
            uuids.add(dto.id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, NearestIntervalDto> nearestIntervalDtoList = utils.findAllNearestByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);

        for (PublicDoctorResponseDto dto : dtoList) {
            doctorCardDtoList.add(
                    new DoctorCardDto(
                            avatarUrls.get(dto.id()),
                            dto,
                            nearestIntervalDtoList.get(dto.id()),
                            durations.get(dto.id()))
            );
        }
        return doctorCardDtoList;
    }

    private List<DoctorPrivateProfileDto> aggregateToPrivate(List<FullPrivateDoctorResponseDto> dtoList) {
        List<DoctorPrivateProfileDto> systemCardDtos = new ArrayList<>();
        List<UUID> uuids = new ArrayList<>();
        for (FullPrivateDoctorResponseDto dto : dtoList) {
            uuids.add(dto.doctor().baseDto().id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);

        for (FullPrivateDoctorResponseDto dto : dtoList) {
            UUID id = dto.doctor().baseDto().id();
            systemCardDtos.add(
                    new DoctorPrivateProfileDto(
                            avatarUrls.get(id),
                            dto,
                            utils.findAllPrivateContactByOwnerId(id),
                            durations.get(id))
            );
        }

        return systemCardDtos;
    }
}