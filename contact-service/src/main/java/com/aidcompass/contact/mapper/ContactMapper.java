package com.aidcompass.contact.mapper;

import com.aidcompass.contact.models.dto.*;
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
    ContactEntity toEntity(ContactCreateDto contactCreateDto);

    List<ContactEntity> toEntityList(List<ContactCreateDto> contactCreateDtoList);

    @Mapping(source = "isPrimary", target = "primary")
    @Mapping(source = "isConfirmed", target = "confirmed")
    @Mapping(source = "type", target = "typeEntity", qualifiedByName = "toTypeEntity")
    ContactEntity toEntity(SystemContactDto systemContactDto);

    List<ContactEntity> formSystemDtoListToEntityList(List<SystemContactDto> systemContactDtoList);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "confirmed", target = "isConfirmed")
    @Mapping(target = "type", expression = "java(contactEntity.getTypeEntity().getType().toString())")
    PrivateContactResponseDto toPrivateResponseDto(ContactEntity contactEntity);

    List<PrivateContactResponseDto> toPrivateResponseDtoList(List<ContactEntity> contactEntityList);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(target = "type", expression = "java(contactEntity.getTypeEntity().getType().toString())")
    PublicContactResponseDto toPublicResponseDto(ContactEntity contactEntity);

    List<PublicContactResponseDto> toPublicResponseDtoList(List<ContactEntity> contactEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isPrimary", target = "primary")
    void updateEntityFromDto(ContactUpdateDto contactUpdateDto, @MappingTarget ContactEntity contactEntity);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "confirmed", target = "isConfirmed")
    @Mapping(source = "typeEntity.type", target = "type")
    SystemContactDto toSystemDto(ContactEntity contactEntity);

    List<SystemContactDto> toSystemDtoList(List<ContactEntity> contactEntityList);
}
