package com.aidcompass.interval.services;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalUpdateDto;
import com.aidcompass.interval.models.overlaps.ValidationInfoDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IntervalService {
    IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto);

    IntervalResponseDto save(UUID ownerId, ValidationInfoDto info);

    List<IntervalResponseDto> saveAll(UUID ownerId, Set<SystemIntervalCreatedDto> dtoSet);

    Set<IntervalResponseDto> cut(AppointmentMarker dto, LocalTime end, Long id);

    List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<IntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end);

    void deleteByOwnerIdAndId(UUID ownerId, Long id);

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    void deleteAllByOwnerId(UUID ownerId);

    boolean findByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date);
}
