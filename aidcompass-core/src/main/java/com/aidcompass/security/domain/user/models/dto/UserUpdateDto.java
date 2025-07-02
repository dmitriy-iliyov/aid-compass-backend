package com.aidcompass.security.domain.user.models.dto;


public record UserUpdateDto(
        String email,
        String password
) { }
