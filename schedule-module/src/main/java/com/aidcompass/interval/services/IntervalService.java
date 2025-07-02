package com.aidcompass.interval.services;

import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IntervalService extends IntervalDeleteService {
    IntervalResponseDto save(UUID ownerId, SystemIntervalCreatedDto dto);

    Set<IntervalResponseDto> cut(AppointmentMarker dto, LocalTime end, Long id);

    List<IntervalResponseDto> findAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    List<IntervalResponseDto> findAllByOwnerIdAndDateInterval(UUID ownerId, LocalDate start, LocalDate end);

    IntervalResponseDto deleteByOwnerIdAndId(UUID ownerId, Long id);

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);

    IntervalResponseDto findByOwnerIdAndStartAndDate(UUID ownerId, LocalTime start, LocalDate date);

    IntervalResponseDto findById(Long id);

    IntervalResponseDto findNearestByOwnerId(UUID ownerId);

    List<IntervalResponseDto> findAllNearestByOwnerIdIn(List<UUID> ownerIds);
}
