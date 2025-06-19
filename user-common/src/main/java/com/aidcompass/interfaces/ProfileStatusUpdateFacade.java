package com.aidcompass.interfaces;


import com.aidcompass.enums.ServiceType;

import java.util.UUID;

public interface ProfileStatusUpdateFacade {
    void updateProfileStatus(ServiceType type, UUID userId);
}
