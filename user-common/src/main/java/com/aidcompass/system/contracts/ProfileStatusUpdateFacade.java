package com.aidcompass.system.contracts;


import com.aidcompass.system.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateFacade {
    void updateProfileStatus(ServiceType type, UUID userId);
}