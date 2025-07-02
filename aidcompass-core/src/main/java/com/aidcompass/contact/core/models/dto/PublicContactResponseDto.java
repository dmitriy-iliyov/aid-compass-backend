package com.aidcompass.contact.core.models.dto;


import com.aidcompass.ContactType;

public record PublicContactResponseDto(
        ContactType type,
        String contact,
        boolean isPrimary
) { }
