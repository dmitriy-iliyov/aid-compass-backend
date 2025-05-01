package com.example.client.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String number,
        Instant createdAt
) { }
