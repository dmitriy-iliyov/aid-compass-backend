package com.aidcompass.work_day;

import com.aidcompass.appointment.contracts.AppointmentService;
import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.appointment.enums.AppointmentStatus;
import com.aidcompass.interval.dto.IntervalResponseDto;
import com.aidcompass.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnifiedWorkDayService implements WorkDayService {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;


    @Override
    public List<String> findListOfTimes(UUID ownerId, LocalDate date) {
        Long duration = appointmentDurationService.findAppointmentDurationByOwnerId(ownerId);
        List<IntervalResponseDto> dtoList = intervalService.findAllByOwnerIdAndDate(ownerId, date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<LocalTime> times = new ArrayList<>();
        for (IntervalResponseDto dto: dtoList) {
            LocalTime time = dto.start();
            long intervalDuration = Duration.between(time, dto.end()).toMinutes();
            for (int i = 0; i < intervalDuration/duration; i++) {
                times.add(time);
                time = time.plusMinutes(duration);
            }
        }
        return times.stream()
                .map(time -> time.format(formatter))
                .toList();
    }

    @Override
    public Map<String, TimeInfo> findPrivateListOfTimes(UUID ownerId, LocalDate date) {

        List<IntervalResponseDto> intervals = intervalService.findAllByOwnerIdAndDate(ownerId, date);
        List<AppointmentResponseDto> appointments =
                appointmentService.findAllByVolunteerIdAndDateAndStatus(ownerId, date, AppointmentStatus.SCHEDULED);

        Map<LocalTime, Pair<LocalTime, TimeInfo>> existsTimeMap = appointments.stream()
                .collect(Collectors.toMap(
                        AppointmentResponseDto::start,
                        appointment -> Pair.of(appointment.end(), new TimeInfo(appointment.id(), 2)))
                );
        intervals.forEach(interval -> existsTimeMap.put(
                interval.start(),
                Pair.of(interval.end(), new TimeInfo(interval.id(), 1)))
        );

        List<LocalTime> times = existsTimeMap.keySet().stream().sorted().toList();
        Long duration = appointmentDurationService.findAppointmentDurationByOwnerId(ownerId);
        Map<LocalTime, TimeInfo> resultMap = new LinkedHashMap<>();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = start.plusMinutes(duration);
        int i = 0;
        while(end.getHour() <= 20) {
            if (end.getHour() == 20 && end.getMinute() != 0) {
                break;
            }
            if (i < times.size()) {
                LocalTime eTime = times.get(i);
                if (end.isBefore(eTime)) {
                    resultMap.put(start, new TimeInfo(null, 0));
                    start = end;
                } else if (end.equals(eTime)) {
                    resultMap.put(start, new TimeInfo(null, 0));
                    resultMap.put(eTime, existsTimeMap.get(eTime).getRight());
                    start = existsTimeMap.get(eTime).getLeft();
                    i++;
                } else {
                    resultMap.put(eTime, existsTimeMap.get(eTime).getRight());
                    start = existsTimeMap.get(eTime).getLeft();
                    i++;
                }
            } else {
                resultMap.put(start, new TimeInfo(null, 0));
                start = end;
            }
            end = start.plusMinutes(duration);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        entry -> entry.getKey().format(formatter),
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));
    }

    @Override
    public void delete(UUID ownerId, LocalDate date) {
        intervalService.deleteAllByOwnerIdAndDate(ownerId, date);
        appointmentService.markCanceledAllByDate(ownerId, date);
    }
}