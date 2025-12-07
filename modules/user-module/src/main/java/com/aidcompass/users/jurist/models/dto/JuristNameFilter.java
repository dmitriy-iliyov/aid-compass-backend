package com.aidcompass.users.jurist.models.dto;

import com.aidcompass.users.general.dto.NameFilter;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class JuristNameFilter extends NameFilter {

    protected JuristType type;

    public JuristNameFilter() {
        super(null, null, null, 0, 10);
    }

    public JuristNameFilter(String secondName, String firstName, String lastName, JuristType type, Integer page, Integer size) {
        super(secondName, firstName, lastName, page, size);
        this.type = type;
    }
}
