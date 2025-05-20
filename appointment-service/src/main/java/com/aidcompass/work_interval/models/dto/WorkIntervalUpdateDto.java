package com.aidcompass.work_interval.models.dto;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.validation.annotation.WorkInterval;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@WorkInterval
public record WorkIntervalUpdateDto(
        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) implements WorkIntervalMarker { }
