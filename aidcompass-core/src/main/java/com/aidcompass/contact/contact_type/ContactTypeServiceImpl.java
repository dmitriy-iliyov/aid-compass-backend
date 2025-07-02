package com.aidcompass.contact.contact_type;

import com.aidcompass.ContactType;
import com.aidcompass.contact.contact_type.mapper.ContactTypeMapper;
import com.aidcompass.contact.contact_type.models.ContactTypeDto;
import com.aidcompass.contact.contact_type.models.ContactTypeEntity;
import com.aidcompass.contact.exceptions.not_found.ContactTypeNotFoundByTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {

    private final ContactTypeRepository repository;
    private final ContactTypeMapper mapper;


    @Transactional
    @Override
    public List<ContactTypeDto> saveAll(List<ContactType> contactTypeList) {
        return mapper.toDtoList(repository.saveAll(mapper.toEntityList(contactTypeList)));
    }

    //cache
    @Transactional(readOnly = true)
    @Override
    public ContactTypeEntity findByType(ContactType type) {
        return repository.findByType(type).orElseThrow(ContactTypeNotFoundByTypeException::new);
    }
}
