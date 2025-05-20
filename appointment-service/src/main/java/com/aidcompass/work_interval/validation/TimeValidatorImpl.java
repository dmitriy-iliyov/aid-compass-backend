package com.aidcompass.work_interval.validation;

import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import com.aidcompass.work_interval.models.WorkIntervalMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TimeValidatorImpl {

    private final AppointmentDurationService service;


    public void validAppointmentDuration() {

    }

    public boolean isWorkIntervalDurationValid(UUID ownerId, WorkIntervalMarker dto) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();

        return passedDuration.equals(duration);
    }

    public void validateWorkIntervalDuration(UUID ownerId, WorkIntervalMarker dto) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();
        if (!passedDuration.equals(duration)) {
            throw new WorkIntervalDurationException();
        }
    }

    public void validWorkIntervalDuration(UUID ownerId, List<WorkIntervalMarker> dtoList) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        List<ErrorDto> errorDtoList = new ArrayList<>();

        for (WorkIntervalMarker dto: dtoList) {
            Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();
            if (!passedDuration.equals(duration)) {
                errorDtoList.add(new ErrorDto("work_interval", "Invalid duration!"));
            }
        }

        if (!errorDtoList.isEmpty()) {
            throw new WorkIntervalsDurationException(errorDtoList);
        }
    }

    public void
}
