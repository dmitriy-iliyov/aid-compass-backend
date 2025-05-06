package com.aidcompass.contact.services;

import com.aidcompass.contact.models.dto.ContactCreateDto;
import com.aidcompass.contact.models.dto.PrivateContactResponseDto;
import com.aidcompass.contact_type.ContactTypeService;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SystemContactFacadeImpl implements SystemContactFacade {

    private final SystemContactService systemContactService;
    private final ContactTypeService typeService;


    @Override
    public boolean existsByContactTypeAndContact(ContactType type, String contact) {
        ContactTypeEntity typeEntity = typeService.findByType(type);
        return systemContactService.existsByTypeEntityAndContact(typeEntity, contact);
    }

    public PrivateContactResponseDto saveLinkedToAccountEmail(UUID ownerId, ContactCreateDto dto) {
        ContactTypeEntity typeEntity = typeService.findByType(dto.type());
        if (!systemContactService.existsByTypeEntityAndContact(typeEntity, dto.contact())) {
            systemContactService.save
        }
        return null;
    }
}
