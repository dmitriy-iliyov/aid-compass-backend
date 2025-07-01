package com.aidcompass.appointment.contracts;

import com.aidcompass.BatchResponse;
import com.aidcompass.appointment.dto.AppointmentResponseDto;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> markBatchAsSkipped(int batchSize);

    BatchResponse<AppointmentResponseDto> findBatchToRemind(int batchSize, int page);
}
