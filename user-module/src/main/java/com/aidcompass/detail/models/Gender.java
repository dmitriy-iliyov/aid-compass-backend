package com.aidcompass.detail.models;

import com.aidcompass.exceptions.UnsupportedGenderException;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
    MALE("Чоловік"),
    FEMALE("Жінка");


    private final String translate;

    Gender(String translate) {
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

    public static Gender toEnum(String translate) {
        List<Gender> values = new ArrayList<>(List.of(Gender.values()));
        return values.stream()
                .filter(v -> v.getTranslate().equals(translate))
                .findFirst()
                .orElseThrow(UnsupportedGenderException::new);
    }
}
