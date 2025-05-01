package com.example.clients;

public record RecoveryRequestDto(
        String resource,
        String password)
{ }
