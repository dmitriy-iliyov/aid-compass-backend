package com.aidcompass;

import com.aidcompass.exceptions.TaskTypeNotFoundByTypeException;
import com.aidcompass.models.entity.ContinueFlagEntity;
import com.aidcompass.models.entity.TaskEntity;
import com.aidcompass.models.TaskStatus;
import com.aidcompass.models.TaskType;
import com.aidcompass.models.entity.TaskTypeEntity;
import com.aidcompass.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleCleanUpService {

    private final AppointmentRepository appointmentRepository;
    private final IntervalRepository intervalRepository;
    private final TaskRepository taskRepository;
    private final TaskTypeRepository taskTypeRepository;
    private final ContinueFlagRepository continueFlagRepository;
    private Map<TaskType, TaskTypeEntity> typesMap;

    @PostConstruct
    public void setTypeEntities() {
        typesMap = new HashMap<>();
        for (TaskType type: TaskType.values()) {
            typesMap.put(type, taskTypeRepository.findByType(type).orElseThrow(TaskTypeNotFoundByTypeException::new));
        }
    }

    @Transactional
    public void deletePastIntervalBatch(int batchSize) {
        ContinueFlagEntity continueFlagEntity = continueFlagRepository.findByTypeEntity(typesMap.get(TaskType.DELETE_INTERVAL));
        if (continueFlagEntity.isShouldContinue()) {
            try {
                TaskEntity taskEntity = new TaskEntity(
                        typesMap.get(TaskType.DELETE_INTERVAL),
                        TaskStatus.NOT_COMPLETED,
                        batchSize,
                        Instant.now(),
                        null
                );
                taskEntity = taskRepository.save(taskEntity);

                LocalDate weakStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                List<Long> deletedIds = intervalRepository.deleteBatchBeforeDate(batchSize, weakStart);

                if (deletedIds.isEmpty()) {
                    continueFlagEntity.setShouldContinue(false);
                    continueFlagRepository.save(continueFlagEntity);
                }

                taskEntity.setStatus(TaskStatus.COMPLETED);
                taskEntity.setEnd(Instant.now());
                taskRepository.save(taskEntity);
            } catch (Exception e) {

            }
        }
    }

    // переводить в новое состояние записи на которые не появился человек

    public void markAppointmentBatchSkipped(int batchSize, LocalDate date) {
        //List<Long> ids = repository.deleteBatchByDate(batchSize, date);
        //log.error("Deleted appointment id list: {}", ids);
    }
}
