package com.aidcompass.contact.contact_filling_progress.models;

import com.aidcompass.security.domain.authority.models.Authority;

import java.util.UUID;

public record ContactFilledEvent(
        UUID userId,
        Authority authority
) { }
