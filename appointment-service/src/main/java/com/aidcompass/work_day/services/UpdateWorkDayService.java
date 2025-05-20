package com.aidcompass.work_day.services;

import com.aidcompass.work_day.models.WorkDayResponseDto;
import com.aidcompass.work_day.models.WorkDayUpdateDto;

public interface UpdateWorkDayService {
    WorkDayResponseDto update(WorkDayUpdateDto dto);
}
