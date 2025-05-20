package com.aidcompass.work_interval;

import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import com.aidcompass.work_interval.services.DeleteWorkIntervalService;
import com.aidcompass.work_interval.services.UpdateWorkIntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationWorkIntervalFacade {

    private final UpdateWorkIntervalService updateService;
    private final DeleteWorkIntervalService deleteService;


    public WorkIntervalResponseDto update(UUID ownerId, WorkIntervalUpdateDto dto) {
        //validation ownership
        return updateService.update(dto);
    }

    public void delete(UUID ownerId, Long id) {
        deleteService.deleteById(id);
    }
}
