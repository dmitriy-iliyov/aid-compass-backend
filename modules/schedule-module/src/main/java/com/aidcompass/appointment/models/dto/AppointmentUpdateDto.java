package com.aidcompass.appointment.models.dto;

import com.aidcompass.appointment.models.enums.AppointmentType;
import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.general.utils.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RequiredArgsConstructor
@Data
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

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LocalTime end;

        @ValidEnum(enumClass = AppointmentType.class, message = "Unsupported appointment type!")
        private final String type;

        @NotBlank(message = "Description shouldn't be empty or blank!")
        @Size(max = 80, message = "Description should less than 40!")
        private final String description;
}