package com.aidcompass.work_interval.models.dto;

import com.aidcompass.work_interval.models.WorkIntervalMarker;
import com.aidcompass.work_interval.validation.annotation.WorkInterval;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

@WorkInterval
public record WorkIntervalUpdateDto(
        @NotNull(message = "Work interval id shouldn't be null!")
        @Positive(message = "Work interval id should be positive!")
        Long id,

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Work interval start time shouldn't be null!")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Work interval start time shouldn't be null!")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Work interval date shouldn't be null!")
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
