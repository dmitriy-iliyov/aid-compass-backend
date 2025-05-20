package com.aidcompass.detail.models.dto;

import java.time.Instant;
import java.util.UUID;

public record SystemDetailDto(
        Long id,
        UUID userId,
        String address,
        Integer workingExperience,
        String aboutMyself,
        Instant createdAt,
        Instant updatesAt
) {}
