package com.aidcompass.enums;

public enum ServiceType {
    DOCTOR_SERVICE("doctors"),
    JURIST_SERVICE("jurists"),
    CUSTOMER_SERVICE("customers");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
