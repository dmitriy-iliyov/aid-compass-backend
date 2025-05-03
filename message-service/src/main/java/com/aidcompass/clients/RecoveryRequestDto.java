package com.aidcompass.clients;

public record RecoveryRequestDto(
        String resource,
        String password)
{ }
