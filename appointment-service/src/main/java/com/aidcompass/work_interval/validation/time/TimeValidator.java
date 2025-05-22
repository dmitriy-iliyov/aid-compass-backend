package com.aidcompass.work_interval.validation.time;

import com.aidcompass.work_interval.models.WorkIntervalMarker;

public interface TimeValidator {

    boolean isWorkIntervalValid(WorkIntervalMarker dto);

    boolean isWorkIntervalTimeValid(WorkIntervalMarker dto);
}
