package com.aidcompass.contact.services;

import com.aidcompass.contact.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.models.dto.system.SystemContactDto;

public interface UnconfirmedContactService {

    void save(SystemContactCreateDto dto);

    SystemContactDto find(String contact);

    void delete(String contact);
}
