package com.aidcompass.work_interval.services;

import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;

import java.util.List;
import java.util.Set;

public interface UpdateWorkIntervalService {
    WorkIntervalResponseDto update(WorkIntervalUpdateDto dto);

    List<WorkIntervalResponseDto> updateAll(Set<WorkIntervalUpdateDto> dtoList);
}
