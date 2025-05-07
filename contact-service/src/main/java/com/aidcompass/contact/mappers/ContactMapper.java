package com.aidcompass.contact.mappers;

import com.aidcompass.contact.models.dto.*;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact_type.ContactTypeService;
import com.aidcompass.contact.models.ContactEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ContactTypeService.class}
)
public interface ContactMapper {

    @Mapping(source = "type", target = "typeEntity", qualifiedByName = "toTypeEntity")
    ContactEntity toEntity(ContactCreateDto dto);

    List<ContactEntity> toEntityList(List<ContactCreateDto> dtoList);

    @Mapping(source = "isPrimary", target = "primary")
    @Mapping(source = "isConfirmed", target = "confirmed")
    @Mapping(source = "type", target = "typeEntity", qualifiedByName = "toTypeEntity")
    ContactEntity toEntity(SystemContactDto dto);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "confirmed", target = "isConfirmed")
    @Mapping(target = "type", expression = "java(entity.getTypeEntity().getType().toString())")
    PrivateContactResponseDto toPrivateResponseDto(ContactEntity entity);

    List<PrivateContactResponseDto> toPrivateResponseDtoList(List<ContactEntity> entityList);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(target = "type", expression = "java(entity.getTypeEntity().getType().toString())")
    PublicContactResponseDto toPublicResponseDto(ContactEntity entity);

    List<PublicContactResponseDto> toPublicResponseDtoList(List<ContactEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isConfirmedParam", target = "confirmed")
    @Mapping(source = "isPrimary", target = "primary")
    void updateEntityFromDto(ContactUpdateDto dto, @MappingTarget ContactEntity entity, boolean isConfirmedParam);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "primary", ignore = true)
    @Mapping(source = "isConfirmedParam", target = "confirmed")
    @Mapping(target = "linkedToAccount", expression = "java(true)")
    void updateEntityFromDto(SystemContactUpdateDto dto, @MappingTarget ContactEntity entity, boolean isConfirmedParam);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "confirmed", target = "isConfirmed")
    @Mapping(source = "typeEntity.type", target = "type")
    SystemContactDto toSystemDto(ContactEntity entity);

    List<SystemContactDto> toSystemDtoList(List<ContactEntity> entityList);
}
