package com.aidcompass.contact.core.mappers;

import com.aidcompass.ContactType;
import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import com.aidcompass.contact.core.models.dto.ContactCreateDto;
import com.aidcompass.contact.core.models.dto.ContactUpdateDto;
import com.aidcompass.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact.core.models.entity.ContactEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:32+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactEntity toEntity(ContactCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        ContactEntity contactEntity = new ContactEntity();

        contactEntity.setContact( dto.contact() );

        return contactEntity;
    }

    @Override
    public List<ContactEntity> toEntityList(List<ContactCreateDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ContactEntity> list = new ArrayList<ContactEntity>( dtoList.size() );
        for ( ContactCreateDto contactCreateDto : dtoList ) {
            list.add( toEntity( contactCreateDto ) );
        }

        return list;
    }

    @Override
    public ContactEntity toEntity(SystemContactDto dto) {
        if ( dto == null ) {
            return null;
        }

        ContactEntity contactEntity = new ContactEntity();

        contactEntity.setPrimary( dto.isPrimary() );
        contactEntity.setLinkedToAccount( dto.isLinkedToAccount() );
        contactEntity.setConfirmed( dto.isConfirmed() );
        contactEntity.setOwnerId( dto.getOwnerId() );
        contactEntity.setContact( dto.getContact() );

        return contactEntity;
    }

    @Override
    public PrivateContactResponseDto toPrivateDto(ContactEntity entity) {
        if ( entity == null ) {
            return null;
        }

        boolean isPrimary = false;
        boolean isConfirmed = false;
        ContactType type = null;
        Long id = null;
        String contact = null;

        isPrimary = entity.isPrimary();
        isConfirmed = entity.isConfirmed();
        type = entityTypeEntityType( entity );
        id = entity.getId();
        contact = entity.getContact();

        PrivateContactResponseDto privateContactResponseDto = new PrivateContactResponseDto( id, type, contact, isPrimary, isConfirmed );

        return privateContactResponseDto;
    }

    @Override
    public List<PrivateContactResponseDto> toPrivateDtoList(List<ContactEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PrivateContactResponseDto> list = new ArrayList<PrivateContactResponseDto>( entityList.size() );
        for ( ContactEntity contactEntity : entityList ) {
            list.add( toPrivateDto( contactEntity ) );
        }

        return list;
    }

    @Override
    public PublicContactResponseDto toPublicDto(ContactEntity entity) {
        if ( entity == null ) {
            return null;
        }

        boolean isPrimary = false;
        ContactType type = null;
        String contact = null;

        isPrimary = entity.isPrimary();
        type = entityTypeEntityType( entity );
        contact = entity.getContact();

        PublicContactResponseDto publicContactResponseDto = new PublicContactResponseDto( type, contact, isPrimary );

        return publicContactResponseDto;
    }

    @Override
    public List<PublicContactResponseDto> toPublicDtoList(List<ContactEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PublicContactResponseDto> list = new ArrayList<PublicContactResponseDto>( entityList.size() );
        for ( ContactEntity contactEntity : entityList ) {
            list.add( toPublicDto( contactEntity ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDto(ContactUpdateDto dto, ContactEntity entity, boolean isConfirmedParam) {
        if ( dto == null ) {
            return;
        }

        if ( dto != null ) {
            entity.setPrimary( dto.isPrimary() );
            if ( dto.contact() != null ) {
                entity.setContact( dto.contact() );
            }
        }
        entity.setConfirmed( isConfirmedParam );
    }

    @Override
    public void updateEntityFromDto(SystemContactUpdateDto dto, ContactEntity entity, boolean isConfirmedParam) {
        if ( dto == null ) {
            return;
        }

        if ( dto != null ) {
            if ( dto.ownerId() != null ) {
                entity.setOwnerId( dto.ownerId() );
            }
            if ( dto.contact() != null ) {
                entity.setContact( dto.contact() );
            }
        }
        entity.setConfirmed( isConfirmedParam );
        entity.setLinkedToAccount( true );
    }

    @Override
    public SystemContactDto toSystemDto(ContactEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SystemContactDto systemContactDto = new SystemContactDto();

        systemContactDto.setPrimary( entity.isPrimary() );
        systemContactDto.setConfirmed( entity.isConfirmed() );
        systemContactDto.setType( entityTypeEntityType( entity ) );
        systemContactDto.setId( entity.getId() );
        systemContactDto.setOwnerId( entity.getOwnerId() );
        systemContactDto.setContact( entity.getContact() );
        systemContactDto.setLinkedToAccount( entity.isLinkedToAccount() );

        return systemContactDto;
    }

    @Override
    public List<SystemContactDto> toSystemDtoList(List<ContactEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SystemContactDto> list = new ArrayList<SystemContactDto>( entityList.size() );
        for ( ContactEntity contactEntity : entityList ) {
            list.add( toSystemDto( contactEntity ) );
        }

        return list;
    }

    private ContactType entityTypeEntityType(ContactEntity contactEntity) {
        if ( contactEntity == null ) {
            return null;
        }
        ContactTypeEntity typeEntity = contactEntity.getTypeEntity();
        if ( typeEntity == null ) {
            return null;
        }
        ContactType type = typeEntity.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }
}
