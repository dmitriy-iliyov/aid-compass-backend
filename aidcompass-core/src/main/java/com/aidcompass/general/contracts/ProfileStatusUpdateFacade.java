package com.aidcompass.general.contracts;


import com.aidcompass.general.contracts.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateFacade {
    void updateProfileStatus(ServiceType type, UUID userId);
}