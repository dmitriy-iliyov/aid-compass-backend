package com.aidcompass.schedule.appointment.models.dto;

import com.aidcompass.core.general.contracts.dto.PageRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class StatusFilter extends PageRequest {

    protected Boolean scheduled;
    protected Boolean canceled;
    protected Boolean completed;

    public StatusFilter() {
        super(0, 10);
    }

    public StatusFilter(Boolean scheduled, Boolean canceled, Boolean completed, Integer page, Integer size) {
        super(page, size);
        this.scheduled = scheduled != null && scheduled;
        this.canceled = canceled != null && canceled;
        this.completed = completed != null && completed;
    }
}
