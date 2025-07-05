package com.aidcompass;

import com.aidcompass.appointment.services.AppointmentOrchestrator;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final IntervalService intervalService;
    private final AppointmentOrchestrator appointmentOrchestrator;


    public List<LocalDate> findMonthDates(UUID ownerId) {
        LocalDate currentDate = LocalDate.now();
        LocalDate end = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(27);
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(20, 0))) {
            currentDate = currentDate.plusDays(2);
        } else {
            currentDate = currentDate.plusDays(1);
        }
        return toDateList(intervalService.findAllByOwnerIdAndDateInterval(ownerId, currentDate, end));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Map<LocalDate, Integer> findPrivateMonthDates(UUID ownerId) {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(27);
        Map<LocalDate, Integer> monthInfo = new LinkedHashMap<>();
        List<LocalDate> dates = toDateList(intervalService.findAllByOwnerIdAndDateInterval(ownerId, start, end));
        List<LocalDate> appointmentDates = appointmentOrchestrator.findMonthDatesByOwnerIdAndCurrentDate(ownerId);
        for (int i = 0; i < 28; i++) {
            LocalDate iDate = start.plusDays(i);
            if (appointmentDates.contains(iDate)) {
                monthInfo.put(iDate, 2);
            } else if (dates.contains(iDate)) {
                monthInfo.put(iDate, 1);
            } else {
                monthInfo.put(iDate, 0);
            }
        }
        return monthInfo.keySet().stream().sorted()
                .collect(Collectors.toMap(
                        Function.identity(),
                        monthInfo::get,
                        (k1, k2) -> k2,
                        LinkedHashMap::new)
                );
    }

    private List<LocalDate> toDateList(List<IntervalResponseDto> dtoList) {
        Set<LocalDate> weakDateSet = new HashSet<>();
        for (IntervalResponseDto intervalDto: dtoList) {
            weakDateSet.add(intervalDto.date());
        }
        return weakDateSet.stream()
                .sorted()
                .toList();
    }
}
