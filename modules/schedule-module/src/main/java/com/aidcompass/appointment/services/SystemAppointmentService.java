package com.aidcompass.appointment.services;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.general.contracts.dto.BatchResponse;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> skipBatch(int batchSize);

    BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page);
}
