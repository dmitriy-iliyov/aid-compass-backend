package com.aidcompass.clients.models;

public record RecoveryRequestDto(
        String resource,
        String password
) { }
