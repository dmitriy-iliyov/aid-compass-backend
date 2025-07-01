package com.aidcompass.appointment.contracts;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> markBatchAsSkipped(int batchSize);
}
