package com.aidcompass.contact.models.dto;


import com.aidcompass.enums.ContactType;

public record PublicContactResponseDto(
        ContactType type,
        String contact,
        boolean isPrimary
) { }
