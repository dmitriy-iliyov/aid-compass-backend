package com.aidcompass.users.jurist.models.dto;

import com.aidcompass.core.general.contracts.dto.PageRequest;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class JuristSpecializationFilter extends PageRequest {

    protected JuristType type;
    protected JuristSpecialization specialization;

    public JuristSpecializationFilter() {
        super(0, 10);
    }

    public JuristSpecializationFilter(Integer page, Integer size, JuristType type, JuristSpecialization specialization) {
        super(page, size);
        this.type = type;
        this.specialization = specialization;
    }
}
