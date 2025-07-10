package com.aidcompass.contact.core.services;

import com.aidcompass.contact.core.mappers.UnconfirmedContactMapper;
import com.aidcompass.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.contact.core.models.entity.UnconfirmedContactEntity;
import com.aidcompass.contact.core.repositories.UnconfirmedContactRepository;
import com.aidcompass.contact.exceptions.not_found.UnconfirmedContactNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnconfirmedContactServiceImpl implements UnconfirmedContactService {

    private final UnconfirmedContactRepository repository;
    private final UnconfirmedContactMapper mapper;


    @Override
    public void save(SystemContactCreateDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public SystemContactDto findById(String contact) {
        UnconfirmedContactEntity entity = repository.findById(contact).orElseThrow(
                UnconfirmedContactNotFoundByIdException::new
        );
        return mapper.toSystemDto(entity);
    }

    @Override
    public void deleteById(String contact) {
        repository.deleteById(contact);
    }

    @Override
    public boolean existsById(String contact) {
        return repository.existsById(contact);
    }
}
