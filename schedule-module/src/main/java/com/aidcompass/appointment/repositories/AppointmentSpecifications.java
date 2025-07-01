package com.aidcompass.appointment.repositories;

import com.aidcompass.appointment.models.AppointmentEntity;
import com.aidcompass.appointment.dto.StatusFilter;
import com.aidcompass.appointment.enums.AppointmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentSpecifications {

    public static Specification<AppointmentEntity> hasStatuses(StatusFilter filter) {
        List<AppointmentStatus> statuses = new ArrayList<>();
        if (filter.scheduled()) statuses.add(AppointmentStatus.SCHEDULED);
        if (filter.completed()) statuses.add(AppointmentStatus.COMPLETED);
        if (filter.canceled())  statuses.add(AppointmentStatus.CANCELED);

        if (statuses.isEmpty()) {
            return Specification.where(null);
        }
        return (r, q, cb) -> r.get("status").in(statuses);
    }

    public static Specification<AppointmentEntity> hasParticipantId(UUID id) {
        return (r, q, cb) -> cb.or(cb.equal(r.get("customerId"), id), cb.equal(r.get("volunteerId"), id));
    }
}
