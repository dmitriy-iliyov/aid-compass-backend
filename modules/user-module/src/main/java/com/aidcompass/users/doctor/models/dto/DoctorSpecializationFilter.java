package com.aidcompass.users.doctor.models.dto;

import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DoctorSpecializationFilter extends PageRequest {
    protected DoctorSpecialization specialization;

    public DoctorSpecializationFilter(DoctorSpecialization specialization, Integer page, Integer size) {
        super(page, size);
        this.specialization = specialization;
    }
}
