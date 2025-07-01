package com.aidcompass.contact.facades;

import com.aidcompass.contracts.SystemContactFacade;
import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact.services.SystemContactService;
import com.aidcompass.contact.validation.validators.PermissionValidator;
import com.aidcompass.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemContactFacadeImpl implements SystemContactFacade {

    private final SystemContactService systemContactService;
    private final PermissionValidator permissionValidator;


    @Override
    public void confirmContactById(Long contactId) {
        systemContactService.confirmContactById(contactId);
    }

    @Override
    public SystemContactDto update(SystemContactUpdateDto dto) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(dto.ownerId(), dto);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactUpdateException(errors);
        }
        return systemContactService.update(dto);
    }
}
