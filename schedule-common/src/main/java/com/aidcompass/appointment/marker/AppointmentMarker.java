package com.aidcompass.appointment.marker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface AppointmentMarker {
    LocalTime start();
    LocalDate date();
    UUID volunteerId();
}

