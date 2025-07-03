package com.aidcompass.system;

import com.aidcompass.general.contracts.dto.BatchRequest;
import com.aidcompass.information.dto.AppointmentDto;

import java.util.Map;
import java.util.UUID;

public interface AppointmentAggregator {
    Map<UUID, AppointmentDto> findBatchToRemind(BatchRequest batchRequest);
}
