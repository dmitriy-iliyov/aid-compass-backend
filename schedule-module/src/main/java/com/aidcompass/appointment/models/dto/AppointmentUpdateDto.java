package com.aidcompass.appointment.models.dto;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class AppointmentUpdateDto implements AppointmentMarker {

        @NotNull(message = "Id shouldn't be null!")
        @Positive(message = "Invalid value!")
        private final Long id;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private UUID volunteerId;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Appointment date must not be null!")
        @Future(message = "Appointment date must be in the future!")
        private final LocalDate date;

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Appointment start time must not be null!")
        private final LocalTime start;


        @Override
        public LocalTime start() {
                return this.start;
        }

        @Override
        public LocalDate date() {
                return this.date;
        }

        @Override
        public UUID volunteerId() {
                return this.volunteerId;
        }
}
