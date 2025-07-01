package com.aidcompass.contact.facades;


import com.aidcompass.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.dto.system.SystemContactCreateDto;
import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.services.UnconfirmedContactService;
import com.aidcompass.contact.validation.validators.CountValidator;
import com.aidcompass.contact_type.ContactTypeService;
import com.aidcompass.contracts.ContactServiceSyncOrchestrator;
import com.aidcompass.enums.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.exceptions.invalid_input.EmailIsInUseException;
import com.aidcompass.exceptions.invalid_input.NotEnoughSpaseForNewContactExceptionBase;
import com.aidcompass.BaseNotFoundException;
import com.aidcompass.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContactServiceSyncOrchestratorImpl implements ContactServiceSyncOrchestrator {

    private final SystemContactService systemContactService;
    private final UnconfirmedContactService unconfirmedContactService;
    private final ContactTypeService typeService;
    private final CountValidator countValidator;


    @Override
    public void save(SystemContactCreateDto dto) {
        if (existsByContactTypeAndContact(dto.type(), dto.contact())) {
            throw new EmailIsInUseException();
        }
        if (!countValidator.hasSpaceForContact(dto.ownerId(), dto)) {
            throw new NotEnoughSpaseForNewContactExceptionBase(
                    List.of(new ErrorDto("contact", "Impossible to add new " + dto.type().toString() + "!"))
            );
        }
        unconfirmedContactService.save(dto);
    }

    @Override
    public Long confirmContact(SystemConfirmationRequestDto requestDto) {
        SystemContactDto systemDto = unconfirmedContactService.findById(requestDto.contact());
        systemDto.setConfirmed(true);
        log.info("contact from cache: " + systemDto);
        systemDto = systemContactService.save(systemDto);
        log.info("save to db: " + systemDto);
        unconfirmedContactService.deleteById(systemDto.getContact());
        return systemDto.getId();
    }

    @Override
    public boolean existsByContactTypeAndContact(ContactType type, String contact) {
        ContactTypeEntity typeEntity = typeService.findByType(type);
        return systemContactService.existsByTypeEntityAndContact(typeEntity, contact) || unconfirmedContactService.existsById(contact);
    }

    @Override
    public SystemContactDto findByContact(String contact) {
        try {
            return systemContactService.findByContact(contact);
        } catch (BaseNotFoundException e) {
            return unconfirmedContactService.findById(contact);
        }
    }
}
