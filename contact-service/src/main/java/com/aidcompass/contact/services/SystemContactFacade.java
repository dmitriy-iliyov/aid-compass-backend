package com.aidcompass.contact.services;

import com.aidcompass.contact_type.models.ContactType;

import java.util.UUID;

public interface SystemContactFacade {

    boolean existsByContactTypeAndContact(ContactType type, String contact);

//    saveLinkedToAccountEmail
}
