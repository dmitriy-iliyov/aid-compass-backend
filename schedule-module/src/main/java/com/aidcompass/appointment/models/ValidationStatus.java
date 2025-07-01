package com.aidcompass.appointment.models;

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
