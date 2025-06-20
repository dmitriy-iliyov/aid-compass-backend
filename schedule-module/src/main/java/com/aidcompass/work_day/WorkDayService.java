package com.aidcompass.work_day;

import com.aidcompass.work_day.models.TimeInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorkDayService {

    List<String> findListOfTimes(UUID ownerId, LocalDate date);

    Map<String, TimeInfo> findListOfPrivateTimes(UUID ownerId, LocalDate date);

    void delete(UUID ownerId, LocalDate date);

}
