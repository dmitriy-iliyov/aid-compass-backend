package com.aidcompass.information.dto;


import com.aidcompass.ContactType;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String secondName,
        String lastName,
        ContactType type,
        String contact
) { }