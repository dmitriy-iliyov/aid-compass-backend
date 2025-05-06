package com.aidcompass.client.models;

import com.aidcompass.contact_type.models.ContactType;

public record ConfirmationRequestDto(
        Long contactId,
        String contact,
        ContactType type
) { }
