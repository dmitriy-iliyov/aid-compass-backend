package com.aidcompass.schedule_filling_progress.models;

import com.aidcompass.security.domain.authority.models.Authority;

import java.util.UUID;

public record ScheduleFilledEvent(
        UUID userId,
        Authority authority
) { }
