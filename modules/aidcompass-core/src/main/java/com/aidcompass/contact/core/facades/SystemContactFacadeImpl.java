package com.aidcompass.contact.core.facades;

import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.contact.core.services.SystemContactService;
import com.aidcompass.contact.core.validation.validators.PermissionValidator;
import com.aidcompass.contact.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;
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
