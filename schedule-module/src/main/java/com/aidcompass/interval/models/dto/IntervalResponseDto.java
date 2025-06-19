package com.aidcompass.interval.models.dto;

import com.aidcompass.interval.models.day.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record IntervalResponseDto(
        Long id,

        @JsonProperty("owner_id")
        UUID ownerId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        Day day
) { }
