package com.aidcompass.contact.core.mappers;

import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.models.entity.UnconfirmedContactEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T22:16:33+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (JetBrains s.r.o.)"
)
@Component
public class UnconfirmedContactMapperImpl implements UnconfirmedContactMapper {

    @Override
    public UnconfirmedContactEntity toEntity(SystemContactCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        String contact = null;
        ContactType type = null;
        UUID ownerId = null;

        contact = dto.contact();
        type = dto.type();
        ownerId = dto.ownerId();

        UnconfirmedContactEntity unconfirmedContactEntity = new UnconfirmedContactEntity( contact, type, ownerId );

        return unconfirmedContactEntity;
    }

    @Override
    public SystemContactDto toSystemDto(UnconfirmedContactEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SystemContactDto systemContactDto = new SystemContactDto();

        systemContactDto.setOwnerId( entity.getOwnerId() );
        systemContactDto.setType( entity.getType() );
        systemContactDto.setContact( entity.getContact() );

        systemContactDto.setPrimary( true );
        systemContactDto.setLinkedToAccount( true );

        return systemContactDto;
    }
}
