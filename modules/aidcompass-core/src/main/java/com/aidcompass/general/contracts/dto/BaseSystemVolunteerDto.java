package com.aidcompass.general.contracts.dto;

import java.util.UUID;

public record BaseSystemVolunteerDto(
        UUID id,
        String firstName,
        String secondName,
        String lastName
) { }
