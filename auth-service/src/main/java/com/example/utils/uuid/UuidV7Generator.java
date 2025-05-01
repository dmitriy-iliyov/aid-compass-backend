package com.example.utils.uuid;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IdGeneratorType(BeforeExecutionUuidGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UuidV7Generator {
}
