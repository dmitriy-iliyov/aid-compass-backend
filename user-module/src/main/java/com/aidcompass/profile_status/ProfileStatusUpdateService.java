package com.aidcompass.profile_status;


import com.aidcompass.system.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateService {

    ServiceType getType();

    int updateProfileStatus(UUID userId);
}
