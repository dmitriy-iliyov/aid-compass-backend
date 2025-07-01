package com.aidcompass.interval.marker;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IntervalMarker {
    LocalTime start();
    LocalTime end();
    LocalDate date();
}
