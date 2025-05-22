package com.aidcompass.work_interval.models;

import java.time.LocalDate;
import java.time.LocalTime;

public interface WorkIntervalMarker {
    LocalTime start();
    LocalTime end();
    LocalDate date();
}
