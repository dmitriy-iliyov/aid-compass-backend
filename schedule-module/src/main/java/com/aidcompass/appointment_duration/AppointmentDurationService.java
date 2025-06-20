package com.aidcompass.appointment_duration;

import com.aidcompass.enums.Authority;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentDurationService {
    Long setAppointmentDuration(UUID ownerId, Authority authority, Long duration);

    Long findAppointmentDurationByOwnerId(UUID ownerId);

    Map<UUID, Long> findAllByOwnerIdIn(List<UUID> ownerId);

    void deleteAppointmentDurationByOwnerId(UUID ownerId);
}
