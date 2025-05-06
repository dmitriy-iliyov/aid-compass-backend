package com.aidcompass.contact.services;

import com.aidcompass.contact.models.dto.SystemContactDto;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;

import java.util.List;
import java.util.UUID;

public interface SystemContactService {

    boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact);

    long countByOwnerIdAndContactType(UUID ownerId, ContactType type);

    List<SystemContactDto> findAllByOwnerId(UUID ownerId);

    SystemContactDto findByContact(String contact);

    SystemContactDto findById(Long contactId);

    boolean isContactConfirmed(Long contactId);

    void confirmContactById(Long contactId);
}
