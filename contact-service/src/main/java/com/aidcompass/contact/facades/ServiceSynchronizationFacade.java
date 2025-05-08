package com.aidcompass.contact.facades;

import com.aidcompass.contact.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.contact.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact_type.models.ContactType;

public interface ServiceSynchronizationFacade {

    void save(SystemContactCreateDto dto);

    Long confirmContact(SystemConfirmationRequestDto requestDto);

    boolean existsByContactTypeAndContact(ContactType type, String contact);

    SystemContactDto findByContact(String contact);
}
