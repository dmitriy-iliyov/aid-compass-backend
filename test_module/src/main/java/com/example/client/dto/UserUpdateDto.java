package com.example.client.dto;


public record UserUpdateDto(
        String email,
        String password,
        String number
) { }
