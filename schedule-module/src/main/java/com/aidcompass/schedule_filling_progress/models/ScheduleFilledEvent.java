package com.aidcompass.schedule_filling_progress.models;

import com.aidcompass.enums.Authority;

import java.util.UUID;

public record ScheduleFilledEvent(
        UUID userId,
        Authority authority
) { }
