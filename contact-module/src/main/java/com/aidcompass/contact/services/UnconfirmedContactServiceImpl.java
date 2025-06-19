package com.aidcompass.contact.services;

import com.aidcompass.contact.mappers.UnconfirmedContactMapper;
import com.aidcompass.contact.models.entity.UnconfirmedContactEntity;
import com.aidcompass.base_dto.SystemContactCreateDto;
import com.aidcompass.base_dto.SystemContactDto;
import com.aidcompass.contact.repositories.UnconfirmedContactRepository;
import com.aidcompass.exceptions.not_found.UnconfirmedContactNotFoundByIdException;
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
