package com.aidcompass.work_day.models;

import com.aidcompass.work_day.validation.annotations.WorkDay;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

@WorkDay
public record WorkDayUpdateDto(

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Work day date shouldn't be null!")
        LocalDate date,

        @JsonProperty("work_intervals")
        @NotEmpty(message = "Work intervals set shouldn't be empty!")
        @Valid
        Set<WorkIntervalUpdateDto> workIntervals
) { }
