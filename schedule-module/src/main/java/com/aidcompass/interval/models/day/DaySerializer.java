package com.aidcompass.interval.models.day;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DaySerializer extends JsonSerializer<Day> {

    public DaySerializer(){}

    @Override
    public void serialize(Day day, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("full_name", day.getFullName());
        gen.writeStringField("short_name", day.getShortName());
        gen.writeEndObject();
    }
}

