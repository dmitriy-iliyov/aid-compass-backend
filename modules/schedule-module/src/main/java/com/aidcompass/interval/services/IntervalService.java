package com.aidcompass.interval.services;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IntervalService {
    IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto);

    Set<IntervalResponseDto> cut(AppointmentMarker dto, Long id);

    List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<LocalDate> findMonthDatesByOwnerId(UUID ownerId, LocalDate start, LocalDate end);

    List<IntervalResponseDto> findAllNearestByOwnerIdIn(Set<UUID> ownerIds);

    IntervalResponseDto findByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date);

    IntervalResponseDto findById(Long id);

    IntervalResponseDto findNearestByOwnerId(UUID ownerId);

    void deleteAllByOwnerId(UUID ownerId);

    IntervalResponseDto deleteByOwnerIdAndId(UUID ownerId, Long id);

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);
}
