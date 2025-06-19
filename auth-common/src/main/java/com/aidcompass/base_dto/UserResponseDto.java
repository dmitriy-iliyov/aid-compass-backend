package com.aidcompass.base_dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        @JsonProperty("created_at")
        String createdAt
) { }
