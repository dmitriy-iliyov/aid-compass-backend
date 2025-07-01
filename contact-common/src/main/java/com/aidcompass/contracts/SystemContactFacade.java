package com.aidcompass.contracts;


import com.aidcompass.dto.system.SystemContactDto;
import com.aidcompass.dto.system.SystemContactUpdateDto;

public interface SystemContactFacade {

    void confirmContactById(Long contactId);

    SystemContactDto update(SystemContactUpdateDto dto);
}
