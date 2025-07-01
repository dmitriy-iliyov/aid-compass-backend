package com.aidcompass.dto;


import com.aidcompass.enums.ContactType;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String secondName,
        String lastName,
        ContactType type,
        String contact
) { }