package com.aidcompass.database_initializer;

import com.aidcompass.models.TaskType;
import com.aidcompass.models.entity.TaskTypeEntity;
import com.aidcompass.repositories.TaskTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class TaskTypeDatabaseInitializer {

    private final TaskTypeRepository repository;


    @PostConstruct
    public void setUpTaskType() {
        List<TaskTypeEntity> entityList = Arrays.stream(TaskType.values()).map(TaskTypeEntity::new).toList();
        repository.saveAll(entityList);
    }
}
