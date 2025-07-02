package com.aidcompass.contact.core.facades;


import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactUpdateDto;

public interface SystemContactFacade {

    void confirmContactById(Long contactId);

    SystemContactDto update(SystemContactUpdateDto dto);
}
