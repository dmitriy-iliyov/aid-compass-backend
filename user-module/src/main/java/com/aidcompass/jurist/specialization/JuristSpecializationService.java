package com.aidcompass.jurist.specialization;

import com.aidcompass.general.exceptions.jurist.JuristSpecializationEntityNotFoundBySpecializationException;
import com.aidcompass.jurist.models.JuristEntity;
import com.aidcompass.jurist.specialization.mapper.JuristSpecializationMapper;
import com.aidcompass.jurist.specialization.models.JuristSpecializationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JuristSpecializationService {

    private final JuristSpecializationRepository repository;
    private final JuristSpecializationMapper mapper;


    @Transactional
    public void saveAll(List<JuristSpecialization> specializationList) {
        List<JuristSpecializationEntity> entityList = mapper.toEntityList(specializationList);
        repository.saveAll(entityList);
    }

    //cache
    @Transactional(readOnly = true)
    public JuristSpecializationEntity findEntityBySpecialization(JuristSpecialization specialization) {
        if (specialization == null) {
            return null;
        }
        return repository.findBySpecialization(specialization).orElseThrow(
                JuristSpecializationEntityNotFoundBySpecializationException::new
        );
    }

    @Transactional(readOnly = true)
    public List<JuristSpecializationEntity> findEntityListBySpecializationList(Set<String> specializations) {
        List<JuristSpecializationEntity> entityList = new ArrayList<>();
        for (String spec: specializations)
            entityList.add(findEntityBySpecialization(JuristSpecialization.toEnum(spec)));
        return entityList;
    }

    @Transactional(readOnly = true)
    public Map<UUID, List<JuristSpecialization>> findAllByJuristIds(List<UUID> ids) {
        List<JuristSpecializationEntity> entityList = repository.findAllByJuristIds(ids);
        Map<UUID, List<JuristSpecialization>> specializationsByJuristId = new HashMap<>();
        for (JuristSpecializationEntity spec : entityList) {
            for (JuristEntity jurist : spec.getJurists()) {
                UUID juristId = jurist.getId();
                specializationsByJuristId
                        .computeIfAbsent(juristId, k -> new ArrayList<>())
                        .add(spec.getSpecialization());
            }
        }
        return specializationsByJuristId;
    }
}
