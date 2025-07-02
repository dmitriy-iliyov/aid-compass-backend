package com.aidcompass.general.contracts;

import com.aidcompass.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.general.contracts.dto.BatchRequest;

public interface NotificationOrchestrator {
    void greeting(BaseSystemVolunteerDto dto);

    Boolean remind(BatchRequest batchRequest);
}
