package com.aidcompass.work_interval.models.dto;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SystemWorkIntervalDto(
        Long id,

        @JsonProperty("owner_id")
        UUID ownerId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) implements WorkIntervalMarker {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof WorkIntervalMarker marker){
            if (marker.start().equals(this.start)) return false;
            if (marker.end().equals(this.end)) return false;
            return !marker.date().equals(this.date);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return start.hashCode() * 31 + end.hashCode() * 31 + date.hashCode();
    }
}

