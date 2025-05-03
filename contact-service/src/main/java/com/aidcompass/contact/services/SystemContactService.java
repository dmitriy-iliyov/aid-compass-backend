package com.aidcompass.contact.services;

import com.aidcompass.contact.models.dto.SystemContactDto;
import com.aidcompass.contact_type.models.ContactType;

import java.util.List;
import java.util.UUID;

public interface SystemContactService {

    boolean existsByContactTypeAndContact(ContactType type, String phoneNumber);

    long countByOwnerIdAndContactType(UUID ownerId, ContactType type);

    List<SystemContactDto> findAllByOwnerId(UUID ownerId);

    SystemContactDto findByContact(String contact);

    boolean isContactConfirmed(Long id);
}
