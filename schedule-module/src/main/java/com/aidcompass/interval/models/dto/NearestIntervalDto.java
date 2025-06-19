package com.aidcompass.interval.models.dto;

import com.aidcompass.interval.models.day.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record NearestIntervalDto(
        Long id,

        @JsonProperty("owner_id")
        UUID ownerId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        Day day
) { }
