package com.aidcompass.confirmation;

import com.aidcompass.exceptions.models.InvalidResourceTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ResourceType {
    EMAIL, PHONE_NUMBER, TELEGRAM;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    static ResourceType toResourceType(String stringType) {
        return Arrays.stream(ResourceType.values())
                .filter(type -> type.toString().equalsIgnoreCase(stringType))
                .findFirst()
                .orElseThrow(InvalidResourceTypeException::new);
    }
}
