package com.aidcompass.detail;

import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;

import java.util.UUID;

public interface DetailService {
//    PrivateDetailResponseDto save(UUID userId, DetailDto dto);

    DetailEntity saveEmpty(UUID userId);

    PrivateDetailResponseDto update(UUID userId, DetailDto dto);
}
