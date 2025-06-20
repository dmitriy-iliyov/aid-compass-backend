package com.aidcompass.interval.mapper;

import com.aidcompass.interval.models.IntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.interval.models.dto.SystemIntervalDto;
import com.aidcompass.interval.models.dto.SystemIntervalUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(
      componentModel = MappingConstants.ComponentModel.SPRING,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IntervalMapper {

    IntervalEntity toEntity(UUID ownerId, SystemIntervalCreatedDto dto);

    default List<IntervalEntity> toEntityList(UUID ownerId, Set<SystemIntervalCreatedDto> dtoSet) {
        return dtoSet.stream()
                .map(dto -> toEntity(ownerId, dto))
                .toList();
    }

    @Mapping(
            target = "day",
            expression = "java(com.aidcompass.interval.models.day.Day.from(entity.getDate()))"
    )
    IntervalResponseDto toDto(IntervalEntity entity);

    List<IntervalResponseDto> toDtoList(List<IntervalEntity> entityList);

    @Mapping(
            target = "day",
            expression = "java(com.aidcompass.interval.models.day.Day.from(entity.getDate()))"
    )
    SystemIntervalDto toSystemDto(IntervalEntity entity);

    List<SystemIntervalDto> toSystemDtoList(List<IntervalEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    void updateEntityFromDto(SystemIntervalUpdateDto dto, @MappingTarget IntervalEntity entity);
}
