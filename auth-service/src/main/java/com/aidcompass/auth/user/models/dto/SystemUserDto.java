package com.aidcompass.auth.user.models.dto;


import com.aidcompass.auth.authority.models.Authority;

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
