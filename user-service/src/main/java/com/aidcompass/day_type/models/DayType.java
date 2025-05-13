package com.aidcompass.day_type.models;

import com.aidcompass.exceptions.InvalidDayTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum DayType {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;


    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public DayType toDayType(String stringDayType) {
        return Arrays.stream(DayType.values()).filter(type -> type.toString().equals(stringDayType)).findFirst().orElseThrow(InvalidDayTypeException::new);
    }
}
