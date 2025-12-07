package com.aidcompass.aggregator.api.jurist;

import com.aidcompass.aggregator.AggregatorUtils;
import com.aidcompass.aggregator.api.jurist.dto.JuristCardDto;
import com.aidcompass.aggregator.api.jurist.dto.JuristPrivateProfileDto;
import com.aidcompass.aggregator.api.jurist.dto.JuristPublicProfileDto;
import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.core.general.contracts.dto.PageResponse;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.general.dto.NameFilter;
import com.aidcompass.users.jurist.models.dto.*;
import com.aidcompass.users.jurist.services.JuristService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JuristAggregatorService {

    private final JuristService juristService;
    private final AggregatorUtils utils;


    public JuristPublicProfileDto findPublicProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPublicJuristResponseDto fullDto = juristService.findFullPublicById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new JuristPublicProfileDto(url, fullDto, utils.findAllContactByOwnerId(id), appointmentDuration);
    }

    public JuristPrivateProfileDto findPrivateProfile(UUID id) {
        String url = utils.findAvatarUrlByOwnerId(id);
        FullPrivateJuristResponseDto fullDto = juristService.findFullPrivateById(id);
        Long appointmentDuration = utils.findDurationByOwnerId(id);
        return new JuristPrivateProfileDto(url, fullDto, utils.findAllPrivateContactByOwnerId(id), appointmentDuration);
    }

    public PageResponse<JuristCardDto> findAllApproved(PageRequest page) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristService.findAllApproved(page);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByTypeAndSpecialization(JuristSpecializationFilter filter) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristService.findAllByTypeAndSpecialization(filter);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByNamesCombination(JuristNameFilter filter) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristService.findAllByTypeAndNamesCombination(filter);
        return new PageResponse<>(
                this.aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristCardDto> findAllByGender(Gender gender, PageRequest page) {
        PageResponse<PublicJuristResponseDto> pageResponse = juristService.findAllByGender(gender, page);
        return new PageResponse<>(
                aggregate(pageResponse.data()),
                pageResponse.totalPage()
        );
    }

    public PageResponse<JuristPrivateProfileDto> findAllUnapproved(PageRequest page) {
        PageResponse<FullPrivateJuristResponseDto> jurists = juristService.findAllUnapproved(page);
        return new PageResponse<>(
                aggregateToPrivate(jurists.data()),
                jurists.totalPage()
        );
    }

    public PageResponse<JuristPrivateProfileDto> findAllUnapprovedByNamesCombination(NameFilter filter) {
        PageResponse<FullPrivateJuristResponseDto> jurists = juristService
                .findAllUnapprovedByNamesCombination(filter);
        return new PageResponse<>(
                aggregateToPrivate(jurists.data()),
                jurists.totalPage()
        );
    }

    @Transactional(noRollbackFor = BaseNotFoundException.class)
    public void delete(UUID id, String password, HttpServletRequest request, HttpServletResponse response) {
        utils.deleteAllUserAlignments(id, password, request, response);
        utils.deleteAllVolunteerAlignments(id);
        juristService.deleteById(id);
    }

    private List<JuristPrivateProfileDto> aggregateToPrivate(List<FullPrivateJuristResponseDto> dtoList) {
        List<JuristPrivateProfileDto> response = new ArrayList<>();
        Set<UUID> uuids = new HashSet<>();
        for (FullPrivateJuristResponseDto dto : dtoList) {
            uuids.add(dto.jurist().baseDto().id());
        }
        Map<UUID, String> avatarUrls = utils.findAllAvatarUrlByOwnerIdIn(uuids);
        Map<UUID, Long> durations = utils.findAllDurationByOwnerIdIn(uuids);
        Map<UUID, List<PrivateContactResponseDto>> contacts = utils.findAllPrivateContactByOwnerIdIn(uuids);

        for (FullPrivateJuristResponseDto dto : dtoList) {
            UUID id = dto.jurist().baseDto().id();
            response.add(
                    new JuristPrivateProfileDto(
                            avatarUrls.get(id),
                            dto,
                            contacts.get(id),
                            durations.get(id))
            );
        }

        return response;
    }

    private List<JuristCardDto> aggregate(List<PublicJuristResponseDto> dtoList) {
        List<JuristCardDto> juristCardDtoList = new ArrayList<>();
        Set<UUID> uuids = new HashSet<>();
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
}