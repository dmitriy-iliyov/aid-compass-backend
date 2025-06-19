package com.aidcompass.appointment.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AppointmentStatus {
    SCHEDULED("Заплановано"),
    COMPLETED("Завершено"),
    CANCELED("Скасовано");
    
    private final String translate;

    AppointmentStatus(String translate) {
        this.translate = translate;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return translate;
    }
}
