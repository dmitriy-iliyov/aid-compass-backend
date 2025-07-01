package com.aidcompass.dto;


import com.aidcompass.enums.ContactType;

public record PublicContactResponseDto(
        ContactType type,
        String contact,
        boolean isPrimary
) { }
