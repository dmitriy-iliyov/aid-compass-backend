package com.aidcompass.contact.mappers;

import com.aidcompass.contact.models.UnconfirmedContactEntity;
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

    @Mapping(target = "isPermit", expression = "java(true)")
    @Mapping(target = "isLinkedToAccount", expression = "java(true)")
    UnconfirmedContactEntity toEntity(SystemContactCreateDto dto);

    SystemContactDto toSystemDto(UnconfirmedContactEntity entity);
}
