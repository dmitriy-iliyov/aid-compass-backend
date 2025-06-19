package com.aidcompass.work_day.models;

import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record WorkDayResponseDto(

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonProperty("work_intervals")
        List<IntervalResponseDto> workIntervals
) { }