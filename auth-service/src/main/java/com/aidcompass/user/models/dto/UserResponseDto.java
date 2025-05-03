package com.aidcompass.user.models.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        Instant createdAt
) { }
