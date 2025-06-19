package com.aidcompass.contact.validation.validators.impl;

import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.contact.validation.validators.ContactOwnershipValidator;
import com.aidcompass.exceptions.invalid_input.OwnerShipException;
import com.aidcompass.models.dto.ErrorDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContactOwnershipValidatorImpl implements ContactOwnershipValidator {

    @Override
    public void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList) {
        Set<Long> currentContactIds = systemContactDtoList
                .stream()
                .map(SystemContactDto::getId)
                .collect(Collectors.toSet());

        for (Long contactId : contactIdList) {
            if (!currentContactIds.contains(contactId)) {
                throw new OwnerShipException(new ErrorDto(contactId.toString(), "Contact isn't yours!"));
            }
        }
    }
}
