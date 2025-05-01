package com.example.contact.models.dto;

import com.example.contact_type.models.ContactType;

import java.util.UUID;

public record SystemContactDto(
        Long id,
        UUID ownerId,
        ContactType type,
        String contact,
        boolean isPrimary,
        boolean isConfirmed,
        boolean isLinkedToAccount
) { }
