package com.aidcompass.detail;

import com.aidcompass.detail.models.ServiceType;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;

import java.util.UUID;

public interface DetailService {
    PrivateDetailResponseDto update(UUID userId, DetailDto dto);

    PrivateDetailResponseDto updateWithCache(UUID userId, DetailDto dto, ServiceType serviceType);
}
