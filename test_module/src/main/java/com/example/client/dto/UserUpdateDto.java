package com.example.clients.user.dto;


public record UserUpdateDto(
        String email,
        String password,
        String number
) { }
