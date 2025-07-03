package com.aidcompass.profile_status;


import com.aidcompass.general.contracts.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateService {

    ServiceType getType();

    int updateProfileStatus(UUID userId);
}
