package com.aidcompass.profile_status.models;

public enum ProfileStatus {
    INCOMPLETE, COMPLETE;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
