package com.aidcompass.schedule.appointment.services;

import com.aidcompass.core.general.contracts.dto.BatchResponse;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> markBatchAsSkipped(int batchSize);

    BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page);
}
