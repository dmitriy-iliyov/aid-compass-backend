package com.aidcompass;

import com.aidcompass.dto.BaseSystemVolunteerDto;

public interface NotificationOrchestrator {
    void greeting(BaseSystemVolunteerDto dto);

    Boolean remind(BatchRequest batchRequest);
}
