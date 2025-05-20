package com.aidcompass.work_interval.mapper;

import com.aidcompass.work_interval.models.WorkIntervalEntity;
import com.aidcompass.work_interval.models.dto.WorkIntervalCreateDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalResponseDto;
import com.aidcompass.work_interval.models.dto.WorkIntervalUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(
      componentModel = MappingConstants.ComponentModel.SPRING,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkIntervalMapper {

    WorkIntervalEntity toEntity(UUID ownerId, WorkIntervalCreateDto dto);

    List<WorkIntervalEntity> toEntityList(UUID ownerId, Set<WorkIntervalCreateDto> dtoSet);

    WorkIntervalResponseDto toDto(WorkIntervalEntity entity);

    List<WorkIntervalResponseDto> toDtoList(List<WorkIntervalEntity> entityList);

    void updateEntityFromDto(WorkIntervalUpdateDto dto, @MappingTarget WorkIntervalEntity entity);
}
