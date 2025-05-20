package com.aidcompass.jurist.models;

public enum JuristSpecialization {
    LAWYER("lawyer"),
    NOTARY("notary");

    private final String value;

    JuristSpecialization(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
