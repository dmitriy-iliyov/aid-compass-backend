package com.aidcompass.appointment.validation;

import com.aidcompass.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.appointment.models.enums.AppointmentStatus;
import com.aidcompass.appointment.models.marker.AppointmentMarker;
import com.aidcompass.appointment.services.AppointmentService;
import com.aidcompass.exceptions.appointment.AppointmentAlreadyExistException;
import com.aidcompass.exceptions.appointment.InvalidTimeToCompleteException;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.services.IntervalService;
import com.aidcompass.appointment.models.dto.AppointmentValidationInfoDto;
import com.aidcompass.appointment.models.enums.ValidationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentTimeValidator {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;


    public AppointmentValidationInfoDto validateVolunteerTime(UUID customerId, AppointmentMarker marker, LocalTime end) {
        List<IntervalResponseDto> existingDtoList = intervalService.findAllByOwnerIdAndDate(marker.volunteerId(), marker.date());
        if (!existingDtoList.isEmpty()) {
            for (IntervalResponseDto existedInterval: existingDtoList) {
                if (existedInterval.start().equals(marker.start()) && existedInterval.end().equals(end)) {
                    return new AppointmentValidationInfoDto(
                            ValidationStatus.MATCHES_WITH_INTERVAL,
                            customerId, marker, existedInterval.id()
                    );
                }
                if (!existedInterval.start().isAfter(marker.start()) && !existedInterval.end().isBefore(end)) {
                    return new AppointmentValidationInfoDto(
                            ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL,
                            customerId, marker, existedInterval.id()
                    );
                }
            }
        }
        return new AppointmentValidationInfoDto(ValidationStatus.NO, null, null, null);
    }

    public void validateCustomerTime(UUID customerId, AppointmentMarker marker) {
        boolean isExist = appointmentService.existsByCustomerIdAndDateAndTimeAndStatus(
                customerId, marker.date(), marker.start(), AppointmentStatus.SCHEDULED
        );
        if (isExist) {
            throw new AppointmentAlreadyExistException();
        }
    }

    public void isCompletePermit(Long id) {
        AppointmentResponseDto dto = appointmentService.findById(id);
        if (!dto.date().equals(LocalDate.now())) {
            throw new InvalidTimeToCompleteException();
        }
    }
}
