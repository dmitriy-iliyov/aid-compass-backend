package com.aidcompass.contact.core.facades;


import com.aidcompass.ContactType;
import com.aidcompass.contact.core.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;

public interface ContactServiceSyncOrchestrator {

    void save(SystemContactCreateDto dto);

    Long confirmContact(SystemConfirmationRequestDto requestDto);

    boolean existsByContactTypeAndContact(ContactType type, String contact);

    SystemContactDto findByContact(String contact);
}
