package com.aidcompass.appointment_duration;

import java.util.UUID;

public interface AppointmentDurationService {
    Long setAppointmentDuration(UUID ownerId, Long duration);

    Long findAppointmentDurationByOwnerId(UUID ownerId);
}
