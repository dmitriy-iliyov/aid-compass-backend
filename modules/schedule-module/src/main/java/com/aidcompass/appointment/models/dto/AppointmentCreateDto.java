package com.aidcompass.appointment.models.dto;

import com.aidcompass.appointment.models.enums.AppointmentType;
import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.general.utils.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentCreateDto(

        @JsonProperty("volunteer_id")
        @NotNull(message = "Volunteer id shouldn't be empty or blank!")
        UUID volunteerId,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Appointment date must not be null!")
        @Future(message = "Appointment date must be in the future!")
        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Appointment start time must not be null!")
        LocalTime start,

        @ValidEnum(enumClass = AppointmentType.class, message = "Unsupported appointment type!")
        String type,

        @NotBlank(message = "Description shouldn't be empty or blank!")
        @Size(max = 80, message = "Description should less than 40!")
        String description
) implements AppointmentMarker { }
