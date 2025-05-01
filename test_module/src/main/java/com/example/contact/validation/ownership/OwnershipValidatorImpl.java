package com.example.contact.validation.ownership;

import com.example.contact.models.dto.SystemContactDto;
import com.example.exceptions.invalid_input.OwnerShipExceptionBase;
import com.example.global_exceptions.dto.ErrorDto;
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
            throw new OwnerShipExceptionBase(new ErrorDto(id.toString(), "Contact isn't yours!"));
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
                throw new OwnerShipExceptionBase(new ErrorDto(contactId.toString(), "Contact isn't yours!"));
            }
        }
    }
}
