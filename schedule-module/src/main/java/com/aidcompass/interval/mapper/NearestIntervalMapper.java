package com.aidcompass.interval.mapper;

import com.aidcompass.interval.models.NearestIntervalEntity;
import com.aidcompass.interval.models.dto.IntervalResponseDto;
import com.aidcompass.interval.models.dto.NearestIntervalDto;
import com.aidcompass.interval.models.dto.SystemIntervalDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NearestIntervalMapper {

    NearestIntervalEntity toEntity(SystemIntervalDto dto);

    NearestIntervalEntity toEntity(IntervalResponseDto dto);

    NearestIntervalDto toDto(NearestIntervalEntity entity);

    List<NearestIntervalDto> toDtoList(Iterable<NearestIntervalEntity> entityList);
}
