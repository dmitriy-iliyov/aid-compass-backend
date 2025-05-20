package com.aidcompass.work_day.services;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
import com.aidcompass.work_interval.services.DeleteWorkIntervalService;
import com.aidcompass.work_interval.services.UpdateWorkIntervalService;
import com.aidcompass.work_interval.services.WorkIntervalService;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UnifiedWorkDayService implements WorkDayService, UpdateWorkDayService, DeleteWorkDayService {

    private final WorkIntervalService service;
    private final UpdateWorkIntervalService updateService;
    private final DeleteWorkIntervalService deleteService;


    @Override
    public WorkDayResponseDto save(UUID ownerId, WorkDayCreateDto dto) {
        List<WorkIntervalResponseDto> dtoList = service.saveAll(ownerId, dto.workIntervals());
        return new WorkDayResponseDto(dto.date(), dtoList);
    }

    @Override
    public WorkDayResponseDto update(WorkDayUpdateDto dto) {
        List<WorkIntervalResponseDto> dtoList = updateService.updateAll(dto.workIntervals());
        return new WorkDayResponseDto(dto.date(), dtoList);
    }

    @Override
    public WorkDayResponseDto findDayByDate(LocalDate date) {
        return new WorkDayResponseDto(date, service.findAllByDate(date));
    }

    @Override
    public List<WorkDayResponseDto> findWeakByDate(UUID ownerId, LocalDate date) {
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<WorkIntervalResponseDto> dtoList = service.findAllByOwnerIdAndDateInterval(ownerId, start, end);

        Map<LocalDate, WorkDayResponseDto> weak = new HashMap<>();
        Set<LocalDate> weakDates = new HashSet<>();

        for (WorkIntervalResponseDto intervalDto: dtoList) {
            LocalDate currentDate = intervalDto.date();
            if (weakDates.add(currentDate)) {
                weak.put(currentDate, new WorkDayResponseDto(currentDate, new ArrayList<>(List.of(intervalDto))));
            } else {
                weak.get(currentDate).workIntervals().add(intervalDto);
            }
        }
        return weak.values().stream().toList();
    }

    @Override
    public void delete(UUID ownerId, LocalDate date) {
        deleteService.deleteAllByOwnerIdAndDate(ownerId, date);
    }
}