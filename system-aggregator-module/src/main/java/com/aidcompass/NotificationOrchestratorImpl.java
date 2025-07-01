package com.aidcompass;

import com.aidcompass.appointment.contracts.SystemAppointmentService;
import com.aidcompass.appointment.dto.AppointmentResponseDto;
import com.aidcompass.contracts.GreetingService;
import com.aidcompass.contracts.InformationService;
import com.aidcompass.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationOrchestratorImpl implements NotificationOrchestrator {

    private final SystemAppointmentService systemAppointmentService;
    private final UserAggregator userAggregator;
    private final InformationService informationService;
    private final GreetingService greetingService;


    @Override
    public void greeting(BaseSystemVolunteerDto dto) {
        PublicContactResponseDto contact = userAggregator.findPrimaryContactByOwnerId(dto.id());
        greetingService.onApproveNotification(
                new UserDto(
                        dto.id(),
                        dto.firstName(),
                        dto.secondName(),
                        dto.lastName(),
                        contact.type(),
                        contact.contact()
                )
        );
    }

    @Override
    public Boolean remind(BatchRequest batchRequest) {
        BatchResponse<AppointmentResponseDto> batchResponse = systemAppointmentService.findBatchToRemind(
                batchRequest.size(), batchRequest.page()
        );
        Map<UUID, AppointmentDto> appointmentMap = batchResponse.batch().stream()
                .collect(Collectors.toMap(
                        AppointmentResponseDto::customerId,
                        dto -> new AppointmentDto(dto.type().toString(), dto.date(), dto.description()))
                );
        Map<UUID, UserDto> userMap = userAggregator.findAllCustomerByIdIn(appointmentMap.keySet());
        List<AppointmentReminderDto> reminderList = appointmentMap.entrySet().stream()
                .map(entry -> new AppointmentReminderDto(userMap.get(entry.getKey()), entry.getValue()))
                .toList();
        informationService.reminderNotification(reminderList);
        return batchResponse.hasNext();
    }
}
