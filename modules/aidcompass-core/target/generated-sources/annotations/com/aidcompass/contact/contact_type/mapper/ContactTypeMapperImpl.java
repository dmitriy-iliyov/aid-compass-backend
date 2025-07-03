package com.aidcompass.contact.contact_type.mapper;

import com.aidcompass.ContactType;
import com.aidcompass.contact.contact_type.models.ContactTypeDto;
import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class ContactTypeMapperImpl implements ContactTypeMapper {

    @Override
    public ContactTypeEntity toEntity(ContactType type) {
        if ( type == null ) {
            return null;
        }

        ContactTypeEntity contactTypeEntity = new ContactTypeEntity();

        contactTypeEntity.setType( type );

        return contactTypeEntity;
    }

    @Override
    public List<ContactTypeEntity> toEntityList(List<ContactType> contactTypeList) {
        if ( contactTypeList == null ) {
            return null;
        }

        List<ContactTypeEntity> list = new ArrayList<ContactTypeEntity>( contactTypeList.size() );
        for ( ContactType contactType : contactTypeList ) {
            list.add( toEntity( contactType ) );
        }

        return list;
    }

    @Override
    public ContactTypeDto toDto(ContactTypeEntity contactTypeEntity) {
        if ( contactTypeEntity == null ) {
            return null;
        }

        Integer id = null;
        ContactType type = null;

        id = contactTypeEntity.getId();
        type = contactTypeEntity.getType();

        ContactTypeDto contactTypeDto = new ContactTypeDto( id, type );

        return contactTypeDto;
    }

    @Override
    public List<ContactTypeDto> toDtoList(List<ContactTypeEntity> contactTypeEntityList) {
        if ( contactTypeEntityList == null ) {
            return null;
        }

        List<ContactTypeDto> list = new ArrayList<ContactTypeDto>( contactTypeEntityList.size() );
        for ( ContactTypeEntity contactTypeEntity : contactTypeEntityList ) {
            list.add( toDto( contactTypeEntity ) );
        }

        return list;
    }
}
