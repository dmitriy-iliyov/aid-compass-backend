package com.aidcompass.jurist;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.PageResponse;
import com.aidcompass.enums.gender.Gender;
import com.aidcompass.interval.dto.NearestIntervalDto;
import com.aidcompass.jurist.contracts.JuristDeleteService;
import com.aidcompass.jurist.contracts.JuristReadService;
import com.aidcompass.jurist.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JuristAggregatorService {

    private final JuristReadService juristReadService;
    private final JuristDeleteService juristDeleteService;
    private final AggregatorUtils utils;


    public JuristPublicProfileDto findPublicProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPublicJuristResponseDto fullDto = juristReadService.findFullPublicById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new JuristPublicProfileDto(url, fullDto, utils.findAllContactByOwnerId(id), appointmentDuration);
    }

    public JuristPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPrivateJuristResponseDto fullDto = juristReadService.findFullPrivateById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new JuristPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id), appointmentDuration);
    }

    public PageResponse<JuristCardDto> findAllApproved(int page, int size) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristReadService.findAllApproved(page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByTypeAndSpecialization(String type, String specialization,
                                                                      int page, int size) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristReadService.findAllByTypeAndSpecialization(type, specialization, page, size);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByNamesCombination(String type,
                                                                 String firstName, String secondName, String lastName,
                                                                 int page, int size) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristReadService.findAllByTypeAndNamesCombination(
                type, firstName, secondName, lastName, page, size
        );
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByGender(Gender gender, int page, int size) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristReadService.findAllByGender(gender, page, size);
        return new PageResponse<>(
                aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristPrivateProfileDto> findAllUnapproved(int page, int size) {
        PageResponse<FullPrivateJuristResponseDto> jurists = juristReadService.findAllUnapproved(page, size);
        return new PageResponse<>(
                aggregateToPrivate(jurists.data()),
                jurists.totalPage()
        );
    }

    public PageResponse<JuristPrivateProfileDto> findAllUnapprovedByNamesCombination(String firstName, String secondName,
                                                                                     String lastName,int page, int size) {
        PageResponse<FullPrivateJuristResponseDto> jurists = juristReadService
                .findAllUnapprovedByNamesCombination(firstName, secondName, lastName, page, size);
        return new PageResponse<>(
                aggregateToPrivate(jurists.data()),
                jurists.totalPage()
        );
    }

    private List<JuristPrivateProfileDto> aggregateToPrivate(List<FullPrivateJuristResponseDto> dtoList) {
        List<JuristPrivateProfileDto> response = new ArrayList<>();
        List<UUID> uuids = new ArrayList<>();
        for (FullPrivateJuristResponseDto dto : dtoList) {
            uuids.add(dto.jurist().baseDto().id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);
        for (FullPrivateJuristResponseDto dto : dtoList) {
            UUID id = dto.jurist().baseDto().id();
            response.add(
                    new JuristPrivateProfileDto(
                            avatarUrls.get(id),
                            dto,
                            utils.findAllPrivateContactByOwnerId(id),
                            durations.get(id))
            );
        }

        return response;
    }

    private List<JuristCardDto> aggregate(List<PublicJuristResponseDto> dtoList) {
        List<JuristCardDto> juristCardDtoList = new ArrayList<>();
        List<UUID> uuids = new ArrayList<>();
        for (PublicJuristResponseDto dto : dtoList) {
            uuids.add(dto.id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, NearestIntervalDto> nearestIntervalDtoList = utils.findAllNearestByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);
        for (PublicJuristResponseDto dto : dtoList) {
            juristCardDtoList.add(
                    new JuristCardDto(
                            avatarUrls.get(dto.id()),
                            dto,
                            nearestIntervalDtoList.get(dto.id()),
                            durations.get(dto.id()))
            );
        }

        return juristCardDtoList;
    }

    public void delete(UUID id) {
        utils.deleteAllAlignments(id);
        utils.deleteAllVolunteerAlignments(id);
        juristDeleteService.deleteById(id);
    }
}
