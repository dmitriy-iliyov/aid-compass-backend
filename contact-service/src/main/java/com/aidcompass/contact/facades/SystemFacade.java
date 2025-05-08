package com.aidcompass.contact.facades;

import com.aidcompass.contact.models.dto.system.SystemContactDto;
import com.aidcompass.contact.models.dto.system.SystemContactUpdateDto;

public interface SystemFacade {

    void confirmContactById(Long contactId);

    SystemContactDto update(SystemContactUpdateDto dto);
}
