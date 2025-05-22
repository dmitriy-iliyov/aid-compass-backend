package com.aidcompass.work_interval.validation.duration;

import com.aidcompass.appointment_duration.AppointmentDurationService;
import com.aidcompass.exceptions.work_interval.WorkIntervalDurationException;
import com.aidcompass.exceptions.work_interval.WorkIntervalsDurationException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import com.aidcompass.work_interval.models.WorkIntervalMarker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DurationValidatorImpl implements DurationValidator {

    private final AppointmentDurationService service;


    public void validateAppointmentDuration() {

    }

    public boolean isWorkIntervalDurationValid(UUID ownerId, WorkIntervalMarker dto) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();

        return passedDuration.equals(duration);
    }

    @Override
    public void validateWorkIntervalDuration(UUID ownerId, WorkIntervalMarker dto) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();
        if (!passedDuration.equals(duration)) {
            throw new WorkIntervalDurationException();
        }
    }

    @Override
    public void validateWorkIntervalDuration(UUID ownerId, Set<WorkIntervalMarker> dtoSet) {
        Long duration = service.findAppointmentDurationByOwnerId(ownerId);

        List<ErrorDto> errorDtoList = new ArrayList<>();

        for (WorkIntervalMarker dto: dtoSet) {
            Long passedDuration = Duration.between(dto.start(), dto.end()).toMinutes();
            if (!passedDuration.equals(duration)) {
                errorDtoList.add(new ErrorDto("work_interval", "Invalid duration!"));
            }
        }

        if (!errorDtoList.isEmpty()) {
            throw new WorkIntervalsDurationException(errorDtoList);
        }
    }
}
