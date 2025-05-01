package com.aidcompass.message.clients;

public record RecoveryRequestDto(
        String resource,
        String password)
{ }
