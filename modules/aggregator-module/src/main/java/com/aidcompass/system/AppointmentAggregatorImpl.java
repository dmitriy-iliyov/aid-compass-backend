package com.aidcompass.system;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.services.SystemAppointmentService;
import com.aidcompass.general.contracts.dto.BatchRequest;
import com.aidcompass.information.dto.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentAggregatorImpl implements AppointmentAggregator {

    private final SystemAppointmentService systemAppointmentService;


    @Override
    public Map<UUID, AppointmentDto> findBatchToRemind(BatchRequest batchRequest) {
        return systemAppointmentService.findBatchToRemind(batchRequest.size(), batchRequest.page())
                .batch().stream()
                .collect(Collectors.toMap(
                        AppointmentResponseDto::customerId,
                        dto -> new AppointmentDto(dto.type().toString(), dto.date(), dto.description()))
                );
    }
}
