package com.aidcompass;

import com.aidcompass.appointment.services.UnifiedAppointmentService;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.contact.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.models.dto.PublicContactResponseDto;
import com.aidcompass.contact.services.ContactService;
import com.aidcompass.models.BaseNotFoundException;
import com.aidcompass.user.services.UserService;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.aidcompass.interval.services.NearestIntervalService;
import com.aidcompass.interval.services.IntervalService;
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
    private final ContactService contactService;
    private final UserService userService;
    private final IntervalService intervalService;
    private final UnifiedAppointmentService unifiedAppointmentService;


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
        return contactService.findAllPublicByOwnerId(id);
    }

    public List<PrivateContactResponseDto> findAllPrivateContactByOwnerId(UUID id) {
        return contactService.findAllPrivateByOwnerId(id);
    }

    public void deleteAllAlignments(UUID id) {
        try {
            avatarService.delete(id);
        } catch (BaseNotFoundException ignore) { }
        contactService.deleteAll(id);
        unifiedAppointmentService.deleteAll(id);
        userService.deleteById(id);
    }

    public void deleteAllVolunteerAlignments(UUID id) {
        durationService.deleteAppointmentDurationByOwnerId(id);
        intervalService.deleteAllByOwnerId(id);
    }
}
