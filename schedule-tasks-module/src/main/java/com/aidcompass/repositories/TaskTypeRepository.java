package com.aidcompass.repositories;

import com.aidcompass.models.TaskType;
import com.aidcompass.models.entity.TaskTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskTypeEntity, Integer> {
    Optional<TaskTypeEntity> findByType(TaskType type);
}
