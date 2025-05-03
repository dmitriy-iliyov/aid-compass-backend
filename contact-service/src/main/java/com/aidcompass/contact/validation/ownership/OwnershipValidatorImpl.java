package com.aidcompass.contact.validation.ownership;

import com.aidcompass.contact.models.dto.SystemContactDto;
import com.aidcompass.exceptions.invalid_input.OwnerShipException;
import com.aidcompass.global_exceptions.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OwnershipValidatorImpl implements OwnershipValidator {

    @Override
    public void assertOwnership(Long id, List<SystemContactDto> systemContactDtoList) {
        Set<Long> currentContactIds = systemContactDtoList
                .stream()
                .map(SystemContactDto::id)
                .collect(Collectors.toSet());

        if (!currentContactIds.contains(id)) {
            throw new OwnerShipException(new ErrorDto(id.toString(), "Contact isn't yours!"));
        }
    }

    @Override
    public void assertOwnership(List<Long> contactIds, List<SystemContactDto> systemContactDtoList) {
        Set<Long> currentContactIds = systemContactDtoList
                .stream()
                .map(SystemContactDto::id)
                .collect(Collectors.toSet());

        for (Long contactId : contactIds) {
            if (!currentContactIds.contains(contactId)) {
                throw new OwnerShipException(new ErrorDto(contactId.toString(), "Contact isn't yours!"));
            }
        }
    }
}
