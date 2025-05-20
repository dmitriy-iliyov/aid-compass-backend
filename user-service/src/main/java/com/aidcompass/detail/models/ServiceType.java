package com.aidcompass.detail.models;

public enum ServiceType {
    DOCTOR_SERVICE("doctor"),
    JURIST_SERVICE("jurist");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
