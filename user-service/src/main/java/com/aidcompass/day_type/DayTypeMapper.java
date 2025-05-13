package com.aidcompass.day_type;

import com.aidcompass.day_type.models.DayType;
import com.aidcompass.day_type.models.DayTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DayTypeMapper {

    DayTypeEntity toEntity(DayType dayType);

    List<DayTypeEntity> toEntityList(List<DayType> dayTypeList);
}

