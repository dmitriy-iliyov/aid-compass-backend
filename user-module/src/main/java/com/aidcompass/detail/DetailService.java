package com.aidcompass.detail;

import com.aidcompass.detail.models.DetailDto;
import com.aidcompass.system.enums.ServiceType;

import java.util.UUID;

public interface DetailService {
    PrivateDetailResponseDto update(UUID userId, DetailDto dto, ServiceType serviceType);
}
