package com.aidcompass.auth.user.models.dto;


public record UserUpdateDto(
        String email,
        String password
) { }
