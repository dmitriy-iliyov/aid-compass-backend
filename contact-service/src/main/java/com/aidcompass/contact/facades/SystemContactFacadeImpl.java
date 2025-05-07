package com.aidcompass.contact.facades;

import com.aidcompass.contact.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.contact.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.services.UnconfirmedContactService;
import com.aidcompass.contact.validation.validators.CountValidator;
import com.aidcompass.contact.validation.validators.PermissionValidator;
import com.aidcompass.contact_type.ContactTypeService;
import com.aidcompass.contact_type.models.ContactType;
import com.aidcompass.contact_type.models.ContactTypeEntity;
import com.aidcompass.exceptions.invalid_input.InvalidContactUpdateException;
import com.aidcompass.exceptions.invalid_input.NotEnoughSpaseForNewContactException;
import com.aidcompass.global_exceptions.BaseNotFoundException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemContactFacadeImpl implements SystemContactFacade {

    private final SystemContactService systemContactService;
    private final UnconfirmedContactService unconfirmedContactService;
    private final ContactTypeService typeService;

    private final PermissionValidator permissionValidator;
    private final CountValidator countValidator;


    @Override
    public void save(SystemContactCreateDto dto) {
        if (!countValidator.hasSpaceForContact(dto.ownerId(), dto)) {
            throw new NotEnoughSpaseForNewContactException(
                    List.of(new ErrorDto("contact", "Impossible to add new " + dto.type().toString() + "!"))
            );
        }
        unconfirmedContactService.save(dto);
    }

    @Override
    public Long confirmContact(SystemConfirmationRequestDto requestDto) {
        SystemContactDto systemDto = unconfirmedContactService.find(requestDto.contact());
        systemDto = systemContactService.save(systemDto);
        unconfirmedContactService.delete(systemDto.contact());
        return systemDto.id();
    }

    @Override
    public boolean existsByContactTypeAndContact(ContactType type, String contact) {
        ContactTypeEntity typeEntity = typeService.findByType(type);
        return systemContactService.existsByTypeEntityAndContact(typeEntity, contact);
    }

    @Override
    public SystemContactDto findByContact(String contact) {
        try {
            return systemContactService.findByContact(contact);
        } catch (BaseNotFoundException e) {
            return unconfirmedContactService.find(contact);
        }
    }

    @Override
    public void confirmContactById(Long contactId) {
        systemContactService.confirmContactById(contactId);
    }

    @Override
    public SystemContactDto update(SystemContactUpdateDto dto) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(dto.ownerId(), dto);
        if (!errors.isEmpty()) {
            throw new InvalidContactUpdateException(errors);
        }
        return systemContactService.update(dto);
    }
}
