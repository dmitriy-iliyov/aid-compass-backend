package com.aidcompass.contracts;


import com.aidcompass.base_dto.SystemConfirmationRequestDto;
import com.aidcompass.base_dto.SystemContactCreateDto;
import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.enums.ContactType;

public interface ContactServiceSyncOrchestrator {

    void save(SystemContactCreateDto dto);

    Long confirmContact(SystemConfirmationRequestDto requestDto);

    boolean existsByContactTypeAndContact(ContactType type, String contact);

    SystemContactDto findByContact(String contact);
}
