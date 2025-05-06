package com.aidcompass.user.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        @JsonProperty("created_at")
        Instant createdAt
) { }
