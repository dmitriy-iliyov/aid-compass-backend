package com.aidcompass.detail;

import com.aidcompass.detail.mapper.DetailMapper;
import com.aidcompass.detail.models.DetailEntity;
import com.aidcompass.detail.models.dto.DetailDto;
import com.aidcompass.detail.models.dto.PrivateDetailResponseDto;
import com.aidcompass.exceptions.DetailEntityNotFoundByUserIdException;
import com.aidcompass.utils.uuid.UuidFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final DetailRepository repository;
    private final DetailMapper mapper;

// на основе роли решать какой сервис использовать для того чтоб установить зависимость
//    @Override
//    public PrivateDetailResponseDto save(UUID userId, DetailDto dto) {
//        DetailEntity entity = repository.save(mapper.toEntity(UuidFactory.generate(), userId, dto));
//        return mapper.toPrivateResponseDto(entity);
//    }

    @Transactional
    @Override
    public DetailEntity saveEmpty(UUID userId) {
        return repository.save(new DetailEntity(userId));
    }

    @Transactional
    @Override
    public PrivateDetailResponseDto update(UUID userId, DetailDto dto) {
        DetailEntity entity = repository.findByUserId(userId).orElseThrow(DetailEntityNotFoundByUserIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        repository.save(entity);
        return mapper.toPrivateResponseDto(entity);
    }
}
