package com.aidcompass.contact.validation.validators.impl;

import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.validation.validators.OwnershipValidator;
import com.aidcompass.exceptions.invalid_input.OwnerShipException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OwnershipValidatorImpl implements OwnershipValidator {

    @Override
    public void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList) {
        Set<Long> currentContactIds = systemContactDtoList
                .stream()
                .map(SystemContactDto::id)
                .collect(Collectors.toSet());

        for (Long contactId : contactIdList) {
            if (!currentContactIds.contains(contactId)) {
                throw new OwnerShipException(new ErrorDto(contactId.toString(), "Contact isn't yours!"));
            }
        }
    }
}
