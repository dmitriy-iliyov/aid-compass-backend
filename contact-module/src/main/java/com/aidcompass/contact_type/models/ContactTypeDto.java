package com.aidcompass.contact_type.models;

import com.aidcompass.enums.ContactType;

public record ContactTypeDto(
        Integer id,
        ContactType type
) { }
