package com.aidcompass.repositories;

import com.aidcompass.exceptions.TaskStatusConvertException;
import com.aidcompass.models.TaskStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskStatus taskStatus) {
        if (taskStatus == null) {
            throw new TaskStatusConvertException();
        }
        return taskStatus.getCode();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer i) {
        if (i == null) {
            throw new TaskStatusConvertException();
        }
        return TaskStatus.fromCode(i);
    }
}
