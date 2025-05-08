package com.aidcompass.contact.mappers;

import com.aidcompass.contact.models.entity.UnconfirmedContactEntity;
import com.aidcompass.contact.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
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

    @Mapping(target = "isPrimary", expression = "java(true)")
    @Mapping(target = "isLinkedToAccount", expression = "java(true)")
    SystemContactDto toSystemDto(UnconfirmedContactEntity entity);
}
