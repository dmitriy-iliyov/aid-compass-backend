package com.example.contacts.models;

public enum ContactType {
    EMAIL, PHONE_NUMBER;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
