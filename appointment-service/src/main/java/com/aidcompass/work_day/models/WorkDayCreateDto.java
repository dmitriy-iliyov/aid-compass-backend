package com.aidcompass.work_day.models;

import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_day.validation.WorkDay;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Set;

@WorkDay
public record WorkDayCreateDto(

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonProperty("work_intervals")
        @Valid
        Set<WorkIntervalCreateDto> workIntervals
) { }