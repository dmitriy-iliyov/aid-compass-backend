package com.aidcompass.system;

import com.aidcompass.enums.ServiceType;
import com.aidcompass.interfaces.ProfileStatusUpdateFacade;
import com.aidcompass.profile_status.ProfileStatusUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class ProfileStatusUpdateFacadeImpl implements ProfileStatusUpdateFacade {

    private final Map<ServiceType, ProfileStatusUpdateService> serviceMap;


    public ProfileStatusUpdateFacadeImpl(List<ProfileStatusUpdateService> services) {
        serviceMap = new HashMap<>();
        for(ProfileStatusUpdateService service: services) {
            serviceMap.put(service.getType(), service);
        }
    }

    @Override
    public void updateProfileStatus(ServiceType type, UUID userId) {
        serviceMap.get(type).updateProfileStatus(userId);
    }
}
