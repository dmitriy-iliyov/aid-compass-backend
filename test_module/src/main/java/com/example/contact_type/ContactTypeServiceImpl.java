package com.example.contact_type;

import com.example.contact_type.mapper.ContactTypeMapper;
import com.example.contact_type.models.ContactType;
import com.example.contact_type.models.ContactTypeDto;
import com.example.contact_type.models.ContactTypeEntity;
import com.example.exceptions.not_found.ContentTypeNotFoundByTypeException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
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

    @Named("toTypeEntity")
    @Transactional(readOnly = true)
    @Override
    public ContactTypeEntity findByType(ContactType type) {
        return repository.findByType(type).orElseThrow(ContentTypeNotFoundByTypeException::new);
    }
}
