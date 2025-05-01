package com.example.contact_type.mapper;

import com.example.contact_type.ContactTypeService;
import com.example.contact_type.models.ContactType;
import com.example.contact_type.models.ContactTypeDto;
import com.example.contact_type.models.ContactTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactTypeMapper {

    ContactTypeEntity toEntity(ContactType type);

    List<ContactTypeEntity> toEntityList(List<ContactType> contactTypeList);

    ContactTypeDto toDto(ContactTypeEntity contactTypeEntity);

    List<ContactTypeDto> toDtoList(List<ContactTypeEntity> contactTypeEntityList);
}
