package com.aidcompass.contact_type.models;

import com.aidcompass.exceptions.invalid_input.InvalidContactTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ContactType {
    EMAIL, PHONE_NUMBER;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    static ContactType toContactType(String stringType) {
        return Arrays.stream(ContactType.values())
                .filter(type -> type.name().equalsIgnoreCase(stringType))
                .findFirst()
                .orElseThrow(InvalidContactTypeException::new);
    }
}
