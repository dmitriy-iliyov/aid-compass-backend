package com.example.rest_clients.user.dto;


public record UserUpdateDto(
        String email,
        String password,
        String number
) { }
