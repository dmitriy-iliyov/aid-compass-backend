package com.aidcompass.contact.contact_type;

import com.aidcompass.contact.contact_type.models.ContactTypeDto;
import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import com.aidcompass.ContactType;

import java.util.List;

public interface ContactTypeService {

    List<ContactTypeDto> saveAll(List<ContactType> contactTypeList);

    ContactTypeEntity findByType(ContactType type);
}
