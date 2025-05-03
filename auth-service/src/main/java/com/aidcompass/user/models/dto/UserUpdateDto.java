package com.aidcompass.user.models.dto;


public record UserUpdateDto(
        String email,
        String password
) { }
