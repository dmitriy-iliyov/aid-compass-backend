package com.example.contact_type.models;

import com.example.exceptions.invalid_input.InvalidContactTypeException;
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
        System.out.println("involved toContactType");
        return Arrays.stream(ContactType.values())
                .filter(type -> type.name().equalsIgnoreCase(stringType))
                .findFirst()
                .orElseThrow(InvalidContactTypeException::new);
    }
}
