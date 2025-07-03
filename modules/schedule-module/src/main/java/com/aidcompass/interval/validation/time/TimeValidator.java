package com.aidcompass.interval.validation.time;


import com.aidcompass.interval.models.marker.IntervalMarker;

public interface TimeValidator {

    boolean isIntervalValid(IntervalMarker dto);

    boolean isIntervalTimeValid(IntervalMarker dto);
}
