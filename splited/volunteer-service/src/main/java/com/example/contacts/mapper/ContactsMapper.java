package com.example.contacts.mapper;

import com.example.contacts.models.ContactsDto;
import com.example.contacts.models.ContactsEntity;
import com.example.utils.MapperUtils;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class})
public interface ContactsMapper {

    @Named("toEntity")
    @Mapping(source = "contactsDto.contacts", target = "contacts", qualifiedByName = "mapToEntity")
    ContactsEntity toEntity(ContactsDto contactsDto);

    @Named("toDto")
    @Mapping(source = "contactsEntity.contacts", target = "contacts", qualifiedByName = "mapToDto")
    ContactsDto toDto(ContactsEntity contactsEntity);
}
