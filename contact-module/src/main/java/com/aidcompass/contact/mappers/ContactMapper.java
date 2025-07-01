package com.aidcompass.contact.mappers;

import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.ContactUpdateDto;
import com.aidcompass.dto.PrivateContactResponseDto;
import com.aidcompass.dto.PublicContactResponseDto;
import com.aidcompass.contact.models.entity.ContactEntity;
import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.dto.system.SystemContactUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactMapper {

    ContactEntity toEntity(ContactCreateDto dto);

    List<ContactEntity> toEntityList(List<ContactCreateDto> dtoList);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "dto.primary", target = "primary")
    @Mapping(source = "dto.linkedToAccount", target = "linkedToAccount")
    @Mapping(source = "dto.confirmed", target = "confirmed")
    ContactEntity toEntity(SystemContactDto dto);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "confirmed", target = "isConfirmed")
    //
    @Mapping(source = "typeEntity.type", target = "type")
    PrivateContactResponseDto toPrivateDto(ContactEntity entity);

    List<PrivateContactResponseDto> toPrivateDtoList(List<ContactEntity> entityList);

    @Mapping(source = "primary", target = "isPrimary")
    @Mapping(source = "typeEntity.type", target = "type")
    PublicContactResponseDto toPublicDto(ContactEntity entity);

    List<PublicContactResponseDto> toPublicDtoList(List<ContactEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isConfirmedParam", target = "confirmed")
    @Mapping(source = "dto.isPrimary", target = "primary")
    void updateEntityFromDto(ContactUpdateDto dto, @MappingTarget ContactEntity entity, boolean isConfirmedParam);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "isConfirmedParam", target = "confirmed")
    @Mapping(target = "linkedToAccount", expression = "java(true)")
    void updateEntityFromDto(SystemContactUpdateDto dto, @MappingTarget ContactEntity entity, boolean isConfirmedParam);

    @Mapping(source = "primary", target = "primary")
    @Mapping(source = "confirmed", target = "confirmed")
    @Mapping(source = "typeEntity.type", target = "type")
    SystemContactDto toSystemDto(ContactEntity entity);

    List<SystemContactDto> toSystemDtoList(List<ContactEntity> entityList);
}
