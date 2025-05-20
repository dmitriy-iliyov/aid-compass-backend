package com.aidcompass.appointment_duration;

import com.aidcompass.appointment_duration.models.AppointmentDurationEntity;
import com.aidcompass.exceptions.DurationNotFoundByOwnerIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AppointmentDurationServiceImpl implements AppointmentDurationService {

    private final AppointmentDurationRepository repository;


    @Transactional
    @Override
    public Long setAppointmentDuration(UUID ownerId, Long duration) {
        try {
            AppointmentDurationEntity entity = repository.findByOwnerId(ownerId).orElseThrow(DurationNotFoundByOwnerIdException::new);
            entity.setAppointmentDuration(duration);
            return repository.save(entity).getAppointmentDuration();
        } catch (DurationNotFoundByOwnerIdException e) {
            AppointmentDurationEntity entity = new AppointmentDurationEntity(ownerId, duration);
            return repository.save(entity).getAppointmentDuration();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Long findAppointmentDurationByOwnerId(UUID ownerId) {
        return repository
                .findByOwnerId(ownerId)
                .orElseThrow(DurationNotFoundByOwnerIdException::new)
                .getAppointmentDuration();
    }
}
