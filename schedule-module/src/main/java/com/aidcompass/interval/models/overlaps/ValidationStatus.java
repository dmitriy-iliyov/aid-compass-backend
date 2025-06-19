package com.aidcompass.interval.models.overlaps;

public enum ValidationStatus {
    OK,
    MATCHES_WITH_INTERVAL,
    APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL,
    ALREADY_EXISTING,
    ANY_FOUND,
    SHOULD_UPDATE,
    SHOULD_CREATE,
    NO
}
