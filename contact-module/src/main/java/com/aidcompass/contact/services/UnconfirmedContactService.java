package com.aidcompass.contact.services;

import com.aidcompass.base_dto.SystemContactCreateDto;
import com.aidcompass.base_dto.SystemContactDto;

public interface UnconfirmedContactService {

    void save(SystemContactCreateDto dto);

    SystemContactDto findById(String contact);

    void deleteById(String contact);

    boolean existsById(String contact);
}
