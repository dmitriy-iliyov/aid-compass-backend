package com.aidcompass.system.models;

public record KafkaMessage<T> (
        EventType type,
        T payload
) { }
