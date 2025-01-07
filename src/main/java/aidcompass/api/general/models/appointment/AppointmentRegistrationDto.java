package aidcompass.api.general.models.appointment;

import java.time.Instant;

public interface AppointmentRegistrationDto {
    Long getUserId();
    Instant getAppointmentDate();
}
