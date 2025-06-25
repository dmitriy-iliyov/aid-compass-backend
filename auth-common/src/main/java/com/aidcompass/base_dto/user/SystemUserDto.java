package com.aidcompass.base_dto;


import com.aidcompass.enums.Authority;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public record SystemUserDto(
        UUID id,
        Long emailId,
        String email,
        String password,
        List<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
