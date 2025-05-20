package com.aidcompass.work_day.models;

import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record WorkDayResponseDto(

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonProperty("work_intervals")
        List<WorkIntervalResponseDto> workIntervals
) { }