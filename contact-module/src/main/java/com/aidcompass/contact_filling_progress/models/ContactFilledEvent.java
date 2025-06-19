package com.aidcompass.contact_filling_progress.models;

import com.aidcompass.enums.Authority;

import java.util.UUID;

public record ContactFilledEvent(
        UUID userId,
        Authority authority
) { }
