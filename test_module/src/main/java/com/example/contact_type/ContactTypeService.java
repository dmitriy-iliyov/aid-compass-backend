package com.example.contact_type;

import com.example.contact_type.models.ContactType;
import com.example.contact_type.models.ContactTypeDto;
import com.example.contact_type.models.ContactTypeEntity;
import org.mapstruct.Named;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactTypeService {

    @Transactional
    List<ContactTypeDto> saveAll(List<ContactType> contactTypeList);

    @Named("toTypeEntity")
    ContactTypeEntity findByType(ContactType type);
}
