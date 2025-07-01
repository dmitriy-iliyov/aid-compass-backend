package com.aidcompass.contact.services;

import com.aidcompass.dto.system.SystemContactCreateDto;
import com.aidcompass.dto.system.SystemContactDto;

public interface UnconfirmedContactService {

    void save(SystemContactCreateDto dto);

    SystemContactDto findById(String contact);

    void deleteById(String contact);

    boolean existsById(String contact);
}
