package aidcompass.api.user.models.dto;

import aidcompass.api.security.models.Role;

public record SecurityUserResponseDto(
        Long id,
        String email,
        Role role
){ }
