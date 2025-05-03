package com.aidcompass.client.dto;


public record UserUpdateDto(
        String email,
        String password,
        String number
) { }
