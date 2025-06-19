package com.aidcompass.contact.services;

import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.base_dto.SystemContactUpdateDto;
import com.aidcompass.enums.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;

import java.util.List;
import java.util.UUID;

public interface SystemContactService {

    SystemContactDto save(SystemContactDto systemDto);

    void confirmContactById(Long contactId);

    boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact);

    boolean isContactConfirmed(Long contactId);

    SystemContactDto findById(Long contactId);

    SystemContactDto findByContact(String contact);

    List<SystemContactDto> findAllByOwnerId(UUID ownerId);

    long countByOwnerIdAndContactType(UUID ownerId, ContactType type);

    SystemContactDto update(SystemContactUpdateDto dto);
}
