package com.aidcompass.contact.contact_type.mapper;

import com.aidcompass.ContactType;
import com.aidcompass.contact.contact_type.models.ContactTypeDto;
import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactTypeMapper {

    ContactTypeEntity toEntity(ContactType type);

    List<ContactTypeEntity> toEntityList(List<ContactType> contactTypeList);

    ContactTypeDto toDto(ContactTypeEntity contactTypeEntity);

    List<ContactTypeDto> toDtoList(List<ContactTypeEntity> contactTypeEntityList);
}
