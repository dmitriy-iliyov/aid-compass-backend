package com.aidcompass;

import com.aidcompass.appointment.contracts.AppointmentService;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.contracts.ContactDeleteService;
import com.aidcompass.contracts.ContactReadService;
import com.aidcompass.contracts.UserOrchestrator;
import com.aidcompass.dto.PrivateContactResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;
import com.aidcompass.interval.contracts.IntervalDeleteService;
import com.aidcompass.interval.dto.NearestIntervalDto;
import com.aidcompass.interval.contracts.NearestIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AggregatorUtils {

    private final AvatarService avatarService;
    private final NearestIntervalService nearestIntervalService;
    private final AppointmentDurationService durationService;
    private final ContactReadService contactReadService;
    private final ContactDeleteService contactDeleteService;
    private final UserOrchestrator userOrchestrator;
    private final IntervalDeleteService intervalService;
    private final AppointmentService unifiedAppointmentService;


    public String findAvatarUrlByOwnerId(UUID id) {
        try {
            return avatarService.findUrlByUserId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, String> findAllAvatarUrlByOwnerIdIn(List<UUID> idList) {
        return avatarService.findAllUrlByOwnerIdIn(idList);
    }

    public Map<UUID, NearestIntervalDto> findAllNearestByOwnerIdIn(List<UUID> idList) {
        return nearestIntervalService.findAll(idList);
    }

    public Long findDurationByOwnerId(UUID id) {
        try {
            return durationService.findAppointmentDurationByOwnerId(id);
        } catch (BaseNotFoundException e) {
            return null;
        }
    }

    public Map<UUID, Long> findAllDurationByOwnerIdIn(List<UUID> ownerIds) {
        return durationService.findAllByOwnerIdIn(ownerIds);
    }

    public List<PublicContactResponseDto> findAllContactByOwnerId(UUID id) {
        return contactReadService.findAllPublicByOwnerId(id);
    }

    public List<PrivateContactResponseDto> findAllPrivateContactByOwnerId(UUID id) {
        return contactReadService.findAllPrivateByOwnerId(id);
    }

    public void deleteAllAlignments(UUID id) {
        try {
            avatarService.delete(id);
        } catch (BaseNotFoundException ignore) { }
        contactDeleteService.deleteAll(id);
        unifiedAppointmentService.deleteAll(id);
        userOrchestrator.deleteById(id);
    }

    public void deleteAllVolunteerAlignments(UUID id) {
        durationService.deleteAppointmentDurationByOwnerId(id);
        intervalService.deleteAllByOwnerId(id);
    }
}
