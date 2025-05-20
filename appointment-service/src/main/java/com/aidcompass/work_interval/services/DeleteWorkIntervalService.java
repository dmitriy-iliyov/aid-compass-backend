package com.aidcompass.work_interval.services;

import java.time.LocalDate;
import java.util.UUID;

public interface DeleteWorkIntervalService {

    void deleteById(Long id);

    void deleteAllByOwnerIdAndDate(UUID ownerId, LocalDate date);
}
