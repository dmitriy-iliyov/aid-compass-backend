package com.aidcompass.contracts;


import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.base_dto.SystemContactUpdateDto;

public interface SystemContactFacade {

    void confirmContactById(Long contactId);

    SystemContactDto update(SystemContactUpdateDto dto);
}
