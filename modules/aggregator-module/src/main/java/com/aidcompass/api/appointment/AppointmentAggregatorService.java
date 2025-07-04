package com.aidcompass.api.appointment;

import com.aidcompass.AggregatorUtils;
import com.aidcompass.api.appointment.dto.CustomerAppointmentDto;
import com.aidcompass.api.appointment.dto.DtoMapper;
import com.aidcompass.api.appointment.dto.PublicVolunteerDto;
import com.aidcompass.api.appointment.dto.VolunteerAppointmentDto;
import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.dto.StatusFilter;
import com.aidcompass.appointment.services.AppointmentOrchestrator;
import com.aidcompass.appointment.services.AppointmentService;
import com.aidcompass.customer.models.PublicCustomerResponseDto;
import com.aidcompass.customer.services.CustomerService;
import com.aidcompass.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.doctor.services.DoctorService;
import com.aidcompass.general.contracts.dto.PageResponse;
import com.aidcompass.jurist.models.dto.PublicJuristResponseDto;
import com.aidcompass.jurist.services.JuristService;
import com.aidcompass.general.exceptions.models.BaseNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentAggregatorService {

    private final AppointmentOrchestrator appointmentOrchestrator;
    private final CustomerService customerService;
    private final DoctorService doctorService;
    private final JuristService juristService;
    private final DtoMapper mapper;
    private final AggregatorUtils utils;
    private final AppointmentService unifiedAppointmentService;


    public VolunteerAppointmentDto findFullAppointment(UUID volunteerId, Long id) {
        AppointmentResponseDto appointment = appointmentOrchestrator.findById(volunteerId, id);
        return new VolunteerAppointmentDto(
                appointment,
                utils.findAvatarUrlByOwnerId(appointment.customerId()),
                customerService.findPublicById(appointment.customerId()),
                utils.findAllContactByOwnerId(appointment.customerId())
        );
    }

    public PageResponse<VolunteerAppointmentDto> findByFilterAndVolunteerId(UUID customerId, StatusFilter filter, int page, int size) {
        PageResponse<AppointmentResponseDto> appointments =
                unifiedAppointmentService.findAllByStatusFilter(customerId, filter, page, size);
        return new PageResponse<>(
                prepareVolunteerAppointmentDtoList(appointments.data()),
                appointments.totalPage()
        );
    }

    private List<VolunteerAppointmentDto> prepareVolunteerAppointmentDtoList(List<AppointmentResponseDto> appointments) {
        Map<UUID, List<AppointmentResponseDto>> customerIdToAppointmentsMap = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentResponseDto::customerId));

        Map<UUID, PublicCustomerResponseDto> customerDtoMap = customerService
                .findAllByIds(customerIdToAppointmentsMap.keySet()).stream()
                .collect(Collectors.toMap(PublicCustomerResponseDto::id, Function.identity()));

        Map<UUID, String> customerAvatars = utils.findAllAvatarUrlByOwnerIdIn(customerDtoMap.keySet().stream().toList());

        return customerIdToAppointmentsMap.entrySet().stream()
                .flatMap(entry -> {
                    UUID customerId = entry.getKey();
                    PublicCustomerResponseDto customer = customerDtoMap.get(customerId);
                    if (customer == null) {
                        log.error("Customer not found for id: {}", customerId);
                        return Stream.empty();
                    }
                    return entry.getValue().stream()
                            .map(appointment -> new VolunteerAppointmentDto(
                                    appointment,
                                    customerAvatars.get(customerId),
                                    customer,
                                    utils.findAllContactByOwnerId(customerId))
                            );
                })
                .sorted(Comparator
                        .comparing((VolunteerAppointmentDto dto) -> dto.appointment().date())
                        .thenComparing(dto -> dto.appointment().start()))
                .toList();
    }

    public PageResponse<CustomerAppointmentDto> findByFilterAndCustomerId(UUID customerId, StatusFilter filter, int page, int size) {
        PageResponse<AppointmentResponseDto> appointments =
                unifiedAppointmentService.findAllByStatusFilter(customerId, filter, page, size);
        return new PageResponse<>(
                prepareCustomerAppointmentDtoList(customerId, appointments.data()),
                appointments.totalPage()
        );
    }

    private List<CustomerAppointmentDto> prepareCustomerAppointmentDtoList(UUID customerId, List<AppointmentResponseDto> appointments) {
        Map<UUID, List<AppointmentResponseDto>> customerIdToAppointmentsMap = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentResponseDto::volunteerId));

        Set<UUID> volunteerIds = appointments.stream()
                .map(AppointmentResponseDto::volunteerId)
                .collect(Collectors.toSet());

        // error
        Map<UUID, PublicVolunteerDto> volunteerDtoMap = new HashMap<>();
        for (UUID id: volunteerIds) {
            try {
                PublicDoctorResponseDto doctor = doctorService.findPublicById(id);
                volunteerDtoMap.put(id, mapper.toVolunteerDto(doctor));
            } catch (BaseNotFoundException e) {
                log.warn("Second not found exception, id {}", id);
                PublicJuristResponseDto jurist = juristService.findPublicById(id);
                volunteerDtoMap.put(id, mapper.toVolunteerDto(jurist));
            }
        }

        Map<UUID, String> volunteerAvatars = utils.findAllAvatarUrlByOwnerIdIn(volunteerIds.stream().toList());

        List<CustomerAppointmentDto> response = customerIdToAppointmentsMap.entrySet().stream()
                .flatMap(entry -> {
                    UUID volunteerId = entry.getKey();
                    PublicVolunteerDto volunteer = volunteerDtoMap.get(volunteerId);
                    if (volunteer == null) {
                        log.error("Volunteer not found for id: {}", customerId);
                        return Stream.empty();
                    }
                    return entry.getValue().stream()
                            .map(appointment -> new CustomerAppointmentDto(
                                    appointment,
                                    volunteerAvatars.get(volunteerId),
                                    volunteer)
                            );
                })
                .sorted(Comparator
                        .comparing((CustomerAppointmentDto dto) -> dto.appointment().date())
                        .thenComparing(dto -> dto.appointment().start()))
                .toList();
        return response;
    }
}