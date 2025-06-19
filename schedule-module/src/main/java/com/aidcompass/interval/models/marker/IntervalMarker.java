package com.aidcompass.interval.models.marker;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IntervalMarker {
    LocalTime start();
    LocalTime end();
    LocalDate date();

    default boolean equals(IntervalMarker other) {
        return this.start().equals(other.start()) &&
                this.end().equals(other.end()) &&
                this.date().equals(other.date());
    }

}
