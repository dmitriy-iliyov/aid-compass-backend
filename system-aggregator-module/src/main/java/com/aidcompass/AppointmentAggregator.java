package com.aidcompass;

import com.aidcompass.dto.AppointmentDto;

import java.util.Map;
import java.util.UUID;

public interface AppointmentAggregator {
    Map<UUID, AppointmentDto> findBatchToRemind(BatchRequest batchRequest);
}
