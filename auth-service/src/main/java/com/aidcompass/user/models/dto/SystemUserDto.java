package com.aidcompass.user.models.dto;


import com.aidcompass.authority.models.Authority;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public record SystemUserDto(
        UUID id,
        String email,
        String password,
        List<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
