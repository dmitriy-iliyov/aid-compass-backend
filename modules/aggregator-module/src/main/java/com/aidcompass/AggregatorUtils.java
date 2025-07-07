package com.aidcompass;

import com.aidcompass.appointment.services.AppointmentService;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.services.ContactService;
import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.aidcompass.interval.services.IntervalDeleteService;
import com.aidcompass.interval.services.NearestIntervalService;
import com.aidcompass.security.domain.user.services.UserOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AggregatorUtils {

    private final AvatarService avatarService;
    private final NearestIntervalService nearestIntervalService;
    private final AppointmentDurationService durationService;
    private final ContactService contactService;
    private final UserOrchestrator userOrchestrator;
    private final IntervalDeleteService intervalService;
    private final AppointmentService appointmentService;


    public String findAvatarUrlByOwnerId(UUID id) {
        try {
            return avatarService.findUrlByUserId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, String> findAllAvatarUrlByOwnerIdIn(Set<UUID> idList) {
        return avatarService.findAllUrlByOwnerIdIn(idList);
    }

    public Map<UUID, NearestIntervalDto> findAllNearestByOwnerIdIn(Set<UUID> idList) {
        return nearestIntervalService.findAll(idList);
    }

    public Long findDurationByOwnerId(UUID id) {
        try {
            return durationService.findByOwnerId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, Long> findAllDurationByOwnerIdIn(Set<UUID> ownerIds) {
        return durationService.findAllByOwnerIdIn(ownerIds);
    }

    public List<PublicContactResponseDto> findAllContactByOwnerId(UUID id) {
        return contactService.findAllPublicByOwnerId(id);
    }

    public Map<UUID, SystemContactDto> findPrimaryContactByOwnerIdIn(Set<UUID> ids) {
        List<SystemContactDto> dtoList = contactService.findAllPrimaryByOwnerIdIn(ids);
        return dtoList.stream().collect(Collectors.toMap(SystemContactDto::getOwnerId, Function.identity()));
    }

    public Map<UUID, List<PrivateContactResponseDto>> findAllPrivateContactByOwnerIdIn(Set<UUID> ids) {
        return contactService.findAllPrivateContactByOwnerIdIn(ids);
    }

    public List<PrivateContactResponseDto> findAllPrivateContactByOwnerId(UUID id) {
        return contactService.findAllPrivateByOwnerId(id);
    }

    public void deleteAllUserAlignments(UUID id) {
        try {
            avatarService.delete(id);
        } catch (BaseNotFoundException ignore) { }
        contactService.deleteAll(id);
        appointmentService.deleteAll(id);
        userOrchestrator.deleteById(id);
    }

    public void deleteAllVolunteerAlignments(UUID id) {
        durationService.deleteByOwnerId(id);
        intervalService.deleteAllByOwnerId(id);
    }
}