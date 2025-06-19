package com.aidcompass.interval.models.day;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = DaySerializer.class)
@JsonDeserialize(using = DayDeserializer.class)
@Getter
public enum Day {
    MONDAY("Понеділок", "Пн"),
    TUESDAY("Вівторок", "Вт"),
    WEDNESDAY("Середа", "Ср"),
    THURSDAY("Четвер", "Чт"),
    FRIDAY("Пʼятниця", "Пт"),
    SATURDAY("Субота", "Сб"),
    SUNDAY("Неділя", "Нд");

    private final String fullName;
    private final String shortName;


    Day(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public static Day from(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return Day.valueOf(dayOfWeek.name());
    }
}

