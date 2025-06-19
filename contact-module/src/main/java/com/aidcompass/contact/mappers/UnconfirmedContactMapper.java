package com.aidcompass.contact.mappers;

import com.aidcompass.contact.models.entity.UnconfirmedContactEntity;
import com.aidcompass.base_dto.SystemContactCreateDto;
import com.aidcompass.base_dto.SystemContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UnconfirmedContactMapper {

    UnconfirmedContactEntity toEntity(SystemContactCreateDto dto);

    @Mapping(target = "primary", expression = "java(true)")
    @Mapping(target = "linkedToAccount", expression = "java(true)")
    SystemContactDto toSystemDto(UnconfirmedContactEntity entity);
}
