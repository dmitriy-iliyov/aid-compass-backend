package com.aidcompass.work_day.services;

import java.time.LocalDate;
import java.util.UUID;

public interface DeleteWorkDayService {
    void delete(UUID ownerId, LocalDate date);
}
