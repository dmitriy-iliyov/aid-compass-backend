package com.aidcompass.appointment.services;

import java.util.List;

public interface SystemAppointmentService {
    List<Long> markBatchAsSkipped(int batchSize);
}
