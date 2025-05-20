package com.aidcompass.work_day.services;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkDayService {
    WorkDayResponseDto save(UUID ownerId, WorkDayCreateDto dto);

    List<WorkDayResponseDto> findWeakByDate(UUID ownerId, LocalDate date);

    WorkDayResponseDto findDayByDate(LocalDate date);
}
