package com.aidcompass.contracts;


import com.aidcompass.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.dto.system.SystemContactCreateDto;
import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.enums.ContactType;

public interface ContactServiceSyncOrchestrator {

    void save(SystemContactCreateDto dto);

    Long confirmContact(SystemConfirmationRequestDto requestDto);

    boolean existsByContactTypeAndContact(ContactType type, String contact);

    SystemContactDto findByContact(String contact);
}
