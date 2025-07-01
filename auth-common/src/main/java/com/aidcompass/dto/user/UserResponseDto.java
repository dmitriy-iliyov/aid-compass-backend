package com.aidcompass.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        @JsonProperty("created_at")
        String createdAt
) { }
