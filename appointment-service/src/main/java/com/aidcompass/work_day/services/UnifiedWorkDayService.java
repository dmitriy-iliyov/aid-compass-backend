package com.aidcompass.work_day.services;

import com.aidcompass.work_day.models.WorkDayCreateDto;
import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;
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
public class UnifiedWorkDayService implements WorkDayService {

    private final WorkIntervalService service;


    @Override
    public WorkDayResponseDto save(UUID ownerId, WorkDayCreateDto dto) {
        List<WorkIntervalResponseDto> dtoList = service.saveAll(ownerId, dto.workIntervals());
        return new WorkDayResponseDto(dto.date(), dtoList);
    }

    @Override
    public WorkDayResponseDto update(WorkDayUpdateDto dto) {
        List<WorkIntervalResponseDto> dtoList = service.updateAll(dto.workIntervals());
        return new WorkDayResponseDto(dto.date(), dtoList);
    }

    @Override
    public WorkDayResponseDto findDayByOwnerIdAndDate(UUID ownerId, LocalDate date) {
        return new WorkDayResponseDto(date, service.findAllByOwnerIdAndDate(ownerId, date));
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
        return weak.values().stream()
                .sorted(Comparator.comparing(WorkDayResponseDto::date))
                .toList();
    }

    @Override
    public List<WorkDayResponseDto> findMonthByDate(UUID ownerId, LocalDate date) {
        LocalDate start = date.withDayOfMonth(1);
        LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

        List<WorkIntervalResponseDto> dtoList = service.findAllByOwnerIdAndDateInterval(ownerId, start, end);

        Map<LocalDate, WorkDayResponseDto> month = new HashMap<>();
        Set<LocalDate> monthDates = new HashSet<>();

        for (WorkIntervalResponseDto intervalDto: dtoList) {
            LocalDate currentDate = intervalDto.date();
            if (monthDates.add(currentDate)) {
                month.put(currentDate, new WorkDayResponseDto(currentDate, new ArrayList<>(List.of(intervalDto))));
            } else {
                month.get(currentDate).workIntervals().add(intervalDto);
            }
        }
        return month.values().stream()
                .sorted(Comparator.comparing(WorkDayResponseDto::date))
                .toList();
    }

    @Override
    public void delete(UUID ownerId, LocalDate date) {
        service.deleteAllByOwnerIdAndDate(ownerId, date);
    }
}